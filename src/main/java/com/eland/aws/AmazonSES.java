package com.eland.aws;


import com.eland.DbProcess.OpvGeneralService;
import com.eland.DbProcess.OpviewNotifyService;
import com.eland.DbProcess.opv_general.Entity.VwMailGroupMappingEntity;
import com.eland.DbProcess.opview_notify.Entity.MailApiSendLogEntity;
import com.eland.api.ResponseJSON.ResponseJSON;
import com.eland.sendMessage.FileProperties;
import com.eland.tools.ImportConfig;
import com.eland.tools.Md5Decode;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class AmazonSES {
    private  OpvGeneralService opvGeneralService;
    private  OpviewNotifyService opviewNotifyService;
    private ImportConfig config;
    private static Set<String> blacklist = new HashSet<String>();
    public AmazonSES(ImportConfig config, OpvGeneralService opvGeneralService, OpviewNotifyService opviewNotifyService) {
        try {
            this.config=config;
            this.opvGeneralService = opvGeneralService;
            this.opviewNotifyService = opviewNotifyService;
            if (blacklist==null||blacklist.isEmpty()){
                blacklist= opvGeneralService.getMailBlacklist();
            }
        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
        }
    }
    private static final Logger awsLog = Logger.getLogger("aws");
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
            Set<String>  blacklistNew =opvGeneralService.getMailBlacklist();
            this.blacklist = blacklistNew;
            awsLog.info("blacklist : " + blacklist);
        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
        }
        return blacklist;
    }

    //取得列表
    public Set<String> getBlacklist() {
        Set<String> blacklist = new HashSet<String>();
        try {
            blacklist = this.blacklist;
            awsLog.info("Get Blacklist - " + blacklist);
        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
        }
        return blacklist;
    }

    //刪除
    public void deleteBlacklist(String email) {
        try {
            blacklist.remove(email);
        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
        }
    }

    public String sendMailBySES(String groupId, String titleText, String contentText) throws Exception {
        String json = "";
        try {
            Gson gson = new Gson();
            String errorMessage = "OK";
            String errorCode = "0";

            awsLog.info("Group Id - " + groupId + " , Title : " + titleText);

            ResponseJSON responseJSON = new ResponseJSON();
            if (groupId == null || groupId.length() < 1) {
                errorMessage = "Group id is null.";
                errorCode = "2.1";
            } else if (titleText == null || titleText.length() < 1) {
                errorMessage = "Title is null.";
                errorCode = "2.2";
            } else if (contentText == null || contentText.length() < 1) {
                errorMessage = "Content is null.";
                errorCode = "2.3";
            } else {

                //取得發信名單
                LinkedList<String> mailTargetList = new LinkedList<String>();
                List<VwMailGroupMappingEntity> vwMailGroupMappingEntityList = opvGeneralService.getEmailByGroupId(groupId);

                for (VwMailGroupMappingEntity vwMailGroupMappingEntity : vwMailGroupMappingEntityList) {
                    mailTargetList.add(vwMailGroupMappingEntity.getMailAddress());
                }

                if (mailTargetList.size() > 0) {
                    responseJSON.setGroupId(groupId);
                    responseJSON.setTitle(titleText);
                    responseJSON.setContent(contentText);
                    responseJSON.setMail(mailTargetList);

                    //向審核者(auditor)發信通知
                    for (String mailTarget : mailTargetList) {

                        if (blacklist.contains(mailTarget)) {
                            awsLog.warn(mailTarget + " is in the blacklist. Don't send mail.");
                            continue;
                        }

                        //判斷mailTarget是否在符合email格式
                        if (!mailTarget.matches(format)){
                            awsLog.warn(mailTarget + " is not email format.");
                            continue;
                        }


                        //發送信件
                        if (send(mailTarget, titleText, contentText)) {
                            awsLog.info("Send mail successfully. - " + mailTarget);
                        } else {
                            awsLog.error("Send mail error - " + mailTarget);
                            errorMessage = "Send mail error!";
                            errorCode = "1.1";
                        }
                        //相隔0.5秒後再發信
                        Thread.sleep(500);
                    }
                } else {
                    errorMessage = "Not find e-mail list.";
                    errorCode = "3.1";
                }
            }
            responseJSON.setErrorMessage(errorMessage);
            responseJSON.setErrorCode(errorCode);
            json = gson.toJson(responseJSON);
            awsLog.info("ResponseJSON : " + json);

        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
            awsLog.error("Send mail error!");
            json = " {\"error_message\":\"Send mail error!\",\"error_code\":\"1.0\"}";
        }

        return json;
    }

    public Boolean send(String mailTarget, String subject, String body) throws Exception {

        Boolean result = false;

        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", config.awsMailProtocol);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", config.awsMailServer);

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(config.awsFromMail, config.awsFromName));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTarget));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(body, "text/html;charset=UTF-8");

        // Add a configuration set header. Comment or delete the
        // next line if you are not using a configuration set
        //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try {
            awsLog.info(mailTarget + " - Sending...");
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(config.awsMailServer, config.awsSmtpUserName, config.awsSmtpPassword);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            awsLog.info(mailTarget + " - Email sent!");
            result = true;
        } catch (Exception e) {
            awsLog.error("The email was not sent.");
            awsLog.error("Error message: " + e.getMessage(), e);
        } finally {
            // Close and terminate the connection.
            transport.close();
        }
        return result;
    }


    public String sendMailReduceFailRateBySES(String groupId, String titleText, String contentText) throws Exception {
        String json = "";
        try {
            Gson gson = new Gson();
            String errorMessage = "OK";
            String errorCode = "0";

            ResponseJSON responseJSON = new ResponseJSON();
            if (groupId == null || groupId.length() < 1) {
                errorMessage = "Group id is null.";
                errorCode = "2.1";
            } else if (titleText == null || titleText.length() < 1) {
                errorMessage = "Title is null.";
                errorCode = "2.2";
            } else if (contentText == null || contentText.length() < 1) {
                errorMessage = "Content is null.";
                errorCode = "2.3";
            } else {

                //取得發信名單
                LinkedList<String> mailTargetList = new LinkedList<String>();

                List<VwMailGroupMappingEntity> mailGroupNotificationMappingDAOMailList = opvGeneralService.getEmailByGroupId(groupId);

                for (VwMailGroupMappingEntity mailGroupNotificationMappingEntity : mailGroupNotificationMappingDAOMailList) {
                    mailTargetList.add(mailGroupNotificationMappingEntity.getMailAddress());
                }


                if (mailTargetList.size() > 0) {
                    responseJSON.setGroupId(groupId);
                    responseJSON.setTitle(titleText);
                    responseJSON.setContent(contentText);
                    responseJSON.setMail(mailTargetList);

                    if (config.snsReduceMailSwitch.equals("ON")) {
                        int count = 1;
                        try {
                            count = Integer.parseInt(config.snsReduceMailCount);
                        } catch (Exception e) {
                            awsLog.error(e.getMessage(), e);
                        }
                        //隨機加入可用的備份信箱 補足信件
                        Random random = new Random();

                        while (mailTargetList.size() < count) {
                            mailTargetList.add(mailTargetList.get(random.nextInt(mailTargetList.size())));
                        }
                    }

                    //向審核者(auditor)發信通知
                    for (String mailTarget : mailTargetList) {

                        //即時黑名單
                        if (blacklist.contains(mailTarget)) {
                            awsLog.warn(mailTarget + " is in the blacklist. Don't send mail.");
                            continue;
                        }

                        //發送信件
                        if (send(mailTarget, titleText, contentText)) {
                            awsLog.info("Send mail successfully. - " + mailTarget);
                        } else {
                            awsLog.error("Send mail error - " + mailTarget);
                            errorMessage = "Send mail error!";
                            errorCode = "1.1";
                        }
                        //相隔3秒後再發信
                        Thread.sleep(3000);
                    }
                } else {
                    errorMessage = "Not find e-mail list.";
                    errorCode = "3.1";
                }
            }
            responseJSON.setErrorMessage(errorMessage);
            responseJSON.setErrorCode(errorCode);
            json = gson.toJson(responseJSON);
            awsLog.info("ResponseJSON : " + json);

        } catch (Exception e) {
            awsLog.error(e.getMessage(), e);
            awsLog.error("Send mail error!");
            json = " {\"error_message\":\"Send mail error!\",\"error_code\":\"1.0\"}";
        }

        return json;
    }

    public void Test(){

        String json = "";
        String errorMessage = "OK";
        String errorCode = "0";
        String ip = "";
        //取得現在時間
        Date nowDate = new Date();
        java.sql.Timestamp now = new java.sql.Timestamp(nowDate.getTime());
        //Log

        MailApiSendLogEntity mailApiSendLog = null;
        try {
            mailApiSendLog = new MailApiSendLogEntity();
            Md5Decode md5Decode = new Md5Decode();
            String pid = ("Test123");
            mailApiSendLog.setPid(pid);
            mailApiSendLog.setStatus("Test");
            mailApiSendLog.setCreateTime(now);
            mailApiSendLog.setSenderIp("test123");
            opviewNotifyService.insertMailApiSendLog(mailApiSendLog);


//            mailApiSendLog = opviewNotifyService.getMailApiSendLogEntity("cf55a92b10dc4bd9e79e400544ae96ac_1646274354948");
//            System.out.println(mailApiSendLog.getSenderMail());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
