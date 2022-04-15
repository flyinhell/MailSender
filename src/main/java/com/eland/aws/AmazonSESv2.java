package com.eland.aws;


import com.eland.DbProcess.OpvGeneralService;
import com.eland.DbProcess.OpviewNotifyService;
import com.eland.DbProcess.opview_notify.Entity.MailApiSendLogEntity;
import com.eland.JSON.SendMailRequestJSON.Attachment;
import com.eland.JSON.SendMailRequestJSON.SendMailJSON;
import com.eland.JSON.SendMailResponseJSON.SendMailResponseJSON;
import com.eland.aws.model.SendMail;
import com.eland.sendMessage.MailSender;
import com.eland.tools.ImportConfig;
import com.eland.tools.Md5Decode;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class AmazonSESv2 {
    private static final Logger awsLog = Logger.getLogger("aws");

    private OpvGeneralService opvGeneralService;
    private OpviewNotifyService opviewNotifyService;
    private ImportConfig config;
    private static Set<String> blacklist = new HashSet<String>();
    public AmazonSESv2(ImportConfig config,OpvGeneralService opvGeneralService, OpviewNotifyService opviewNotifyService) {
        try {
            this.config=config;
            this.opvGeneralService = opvGeneralService;
            this.opviewNotifyService = opviewNotifyService;
            if (blacklist==null||blacklist.isEmpty()){
                blacklist= opvGeneralService.getMailBlacklist();
            }
            config.importAllowFromMailList();
        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
        }
    }
    private String awsFromMail;
    private String awsFromName;
    private String subject;
    private String content;
    private List<String> receiverMail = null;
    private List<Attachment> attachmentList = null;

    public static String format = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$";
    //新增
    public void addBlacklist(String mailAddress) {
        try {
            blacklist.add(mailAddress);
            awsLog.info("Insert blacklist success - " + mailAddress);
            awsLog.info("blacklist : " + blacklist);

        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
        }
    }

    //讀取
    public  Set<String> loadBlacklist() {
        try {
            Set<String> blacklistNew = opvGeneralService.getMailBlacklist();
            this.blacklist = blacklistNew;
            awsLog.info("blacklist : " + blacklist);
        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
        }
        return blacklist;

    }

    public String sendMailBySES(HttpServletRequest request, String txtInputJson) {
        String json = "";

        awsLog.info(txtInputJson);
        String errorMessage = "OK";
        String errorCode = "0";
        String ip = "";

        //取得現在時間
        Date nowDate = new Date();
        java.sql.Timestamp now = new java.sql.Timestamp(nowDate.getTime());

        try {
            //Log
            MailApiSendLogEntity mailApiSendLog = new MailApiSendLogEntity();
            Md5Decode md5Decode = new Md5Decode();
            String pid = (md5Decode.md5(txtInputJson + "_" + Thread.currentThread().getName()) + "_" +
                    System.currentTimeMillis());
            mailApiSendLog.setPid(pid);
            mailApiSendLog.setStatus("Processing");
            mailApiSendLog.setCreateTime(now);


            Gson gson = new Gson();
            //取得真正使用者ip位置，避免連線過程中的因經過反向代理服務(nginx)，導致只能取得代理服務的IP
            if (request.getHeader("X-Real-IP") != null) {
                ip = request.getHeader("X-Real-IP");
            } else {
                ip = request.getRemoteAddr();
            }
            mailApiSendLog.setSenderIp(ip);
            opviewNotifyService.insertMailApiSendLog(mailApiSendLog);
            mailApiSendLog = opviewNotifyService.getMailApiSendLogEntity(pid);


            SendMailJSON sendMailJSON = new SendMailJSON();
            sendMailJSON = gson.fromJson(txtInputJson, SendMailJSON.class);
            SendMailResponseJSON sendMailResponseJSON = new SendMailResponseJSON();

            LinkedList<String> mailTargetList = new LinkedList<String>();
            LinkedList<String> sendMailList = new LinkedList<String>();
            LinkedList<String> failMailList = new LinkedList<String>();

            if (sendMailJSON.getSender() == null || sendMailJSON.getSender().length() < 1) {
                errorMessage = "Sender is null.";
                errorCode = "2.1";
            } else if (sendMailJSON.getSenderMail() == null || sendMailJSON.getSenderMail().length() < 1) {
                errorMessage = "Sender Mail is null.";
                errorCode = "2.2";
            } else if (sendMailJSON.getSubject() == null || sendMailJSON.getSubject().length() < 1) {
                errorMessage = "Subject is null.";
                errorCode = "2.3";
            } else if (sendMailJSON.getContent() == null || sendMailJSON.getContent().length() < 1) {
                errorMessage = "Content  is null.";
                errorCode = "2.4";
            } else if (sendMailJSON.getRecipientEmail() == null || sendMailJSON.getRecipientEmail().size() < 1) {
                errorMessage = "Recipient Mail is null.";
                errorCode = "2.5";
            } else if (!config.allowFromMailList.contains(sendMailJSON.getSenderMail())) {
                errorMessage = "Unknown Sender Mail.";
                errorCode = "2.6";
            } else {
                awsFromName = sendMailJSON.getSender();
                awsFromMail = sendMailJSON.getSenderMail();
                subject = sendMailJSON.getSubject();
                content = sendMailJSON.getContent();
                receiverMail = sendMailJSON.getRecipientEmail();

                mailApiSendLog.setSender(awsFromName);
                mailApiSendLog.setSenderMail(awsFromMail);
                mailApiSendLog.setSubject(subject);
                mailApiSendLog.setToMail(receiverMail.toString());


                //取得發信名單
                for (String email : receiverMail) {
                    mailTargetList.add(email);
                }

                if (sendMailJSON.getAttachment() != null && sendMailJSON.getAttachment().size() > 0) {
                    awsLog.info("Attachment!");
                    attachmentList = sendMailJSON.getAttachment();
                    List<String> attachmentName = new LinkedList<String>();
                    long fileSize = 0;
                    File file = null;
                    //base64轉檔案至指定路徑

                    for (Attachment attachment : attachmentList) {
                        awsLog.info("Base64 : " + attachment.getBase64());
                        awsLog.info("File Path : " + config.attachmentFilePath + attachment.getAttachmentName());
                        attachmentName.add(attachment.getAttachmentName());
                        if (decryptByBase64(attachment.getBase64(), config.attachmentFilePath + attachment.getAttachmentName())) {

                            //取得檔案大小
                            file = new File(config.attachmentFilePath + attachment.getAttachmentName());
                            if (file.exists()) {
                                FileInputStream fis = null;
                                fis = new FileInputStream(file);
                                fileSize += fis.available();
                            } else {
                                file.createNewFile();
                                errorMessage = "Attachment Decrypt By Base64 Fail.";
                                errorCode = "2.7";
                                awsLog.info(attachment.getAttachmentName() + " is not exists.");
                                break;
                            }
                            //如果檔案超過10Mb，則拒絕傳送
                            if (fileSize > 1073741824) {
                                errorMessage = "Attachment Size Can't Over 10MB.";
                                errorCode = "2.8";
                                awsLog.info("Attachment Size Over 10MB! Reject Send!");
                                break;
                            }

                        } else {
                            errorMessage = "Attachment Decrypt By Base64 Fail.";
                            errorCode = "2.7";
                            break;
                        }
                    }
                    mailApiSendLog.setAttachment(attachmentName.toString());
                }
            }

            if (!errorCode.equals("0")) {
                sendMailResponseJSON.setErrorCode(errorCode);
                sendMailResponseJSON.setErrorMessage(errorMessage);
                json = gson.toJson(sendMailResponseJSON);
                awsLog.error(json);
                //Insert Log
                mailApiSendLog.setStatus("Fail");
                mailApiSendLog.setResponseJson(json);
                Date endDate = new Date();

                mailApiSendLog.setEndTime(new java.sql.Timestamp(endDate.getTime()));
                opviewNotifyService.insertMailApiSendLog(mailApiSendLog);


                return json;
            }
            if (mailTargetList.size() > 0) {


                //向審核者(auditor)發信通知
                for (String mailTarget : mailTargetList) {
                    SendMail sendMail = new SendMail(config.awsMailServer, config.awsMailProtocol, config.awsSmtpUserName, config.awsSmtpPassword, awsFromName, awsFromMail, config.attachmentFilePath);

                    //判斷mailTarget是否在黑名單內
                    if (blacklist.contains(mailTarget)) {
                        awsLog.warn(mailTarget + " is in the blacklist. Don't send mail.");
                        failMailList.add(mailTarget);
                        errorMessage+=" ("+mailTarget +"  is in the blacklist. Don't send mail.)";
                        continue;
                    }

                    //判斷mailTarget是否在符合email格式
                    if (!mailTarget.matches(format)){
                        awsLog.warn(mailTarget + " is not email format.");
                        failMailList.add(mailTarget);
                        errorMessage+=" ("+mailTarget +"  is not email format.)";
                        continue;
                    }

                    //發送信件
                    if (attachmentList == null || attachmentList.size() < 1) {
                        //無附件
                        if (sendMail.send(mailTarget, subject, content)) {
                            awsLog.info("Send mail successfully. - " + mailTarget);
                            sendMailList.add(mailTarget);
                        } else {
                            //try again
                            Thread.sleep(1000);
                            if (sendMail.send(mailTarget, subject, content)) {
                                awsLog.info("Send mail successfully. - " + mailTarget);
                                sendMailList.add(mailTarget);
                            } else {
                                awsLog.error("Send mail error - " + mailTarget);
                                failMailList.add(mailTarget);
                                errorMessage = "Send Mail Error.";
                                errorCode = "1.1";
                            }
                        }
                    } else {
                        //有附件
                        if (sendMail.send(mailTarget, subject, content, attachmentList)) {
                            awsLog.info("Send mail successfully. - " + mailTarget);
                            sendMailList.add(mailTarget);
                        } else {
                            //try again
                            Thread.sleep(1000);
                            if (sendMail.send(mailTarget, subject, content, attachmentList)) {
                                awsLog.info("Send mail successfully. - " + mailTarget);
                                sendMailList.add(mailTarget);
                            } else {
                                awsLog.error("Send mail error - " + mailTarget);
                                failMailList.add(mailTarget);
                                errorMessage = "Send Mail Error.";
                                errorCode = "1.1";
                            }
                        }
                    }

                    //相隔0.5秒後再發信
                    Thread.sleep(500);
                }
            } else {
                errorMessage = "No Email Can Send.";
                errorCode = "3.1";
            }

            sendMailResponseJSON.setSubject(subject);
            sendMailResponseJSON.setContent(content);
            sendMailResponseJSON.setSendMail(sendMailList);
            sendMailResponseJSON.setFailMail(failMailList);
            sendMailResponseJSON.setErrorCode(errorCode);
            sendMailResponseJSON.setErrorMessage(errorMessage);
            json = gson.toJson(sendMailResponseJSON);

            //Insert Log
            mailApiSendLog.setStatus("Done");
            mailApiSendLog.setResponseJson(json);
            Date endDate = new Date();

            mailApiSendLog.setEndTime(new java.sql.Timestamp(endDate.getTime()));
            opviewNotifyService.insertMailApiSendLog(mailApiSendLog);


        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
            awsLog.error("Unknown Error.");
            json = " {\"error_message\":\"Unknown Error.\",\"error_code\":\"1.0\"}";
        }

        return json;
    }

    //BASE64轉檔案
    public static boolean decryptByBase64(String base64, String filePath) {
        if (base64 == null && filePath == null) {
            return false;
        }
        try {
            Files.write(Paths.get(filePath), Base64.getDecoder().decode(base64), StandardOpenOption.CREATE);

        } catch (IOException e) {
            awsLog.error(e.getMessage(), e);
            return false;

        }
        return true;
    }



}
