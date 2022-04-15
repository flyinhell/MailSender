package com.eland.aws;

import com.eland.DbProcess.OpvGeneralService;
import com.eland.DbProcess.OpviewNotifyService;
import com.eland.DbProcess.opview_notify.Entity.MailSnsLogEntity;
import com.eland.SnsJSON.BouncedRecipient;
import com.eland.SnsJSON.SnsResponseJSON;

import com.eland.sendMessage.SendMethods;
import com.eland.tools.ImportConfig;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zhenfu on 2022/02/17.
 */
public class SimpleNotificationService {

    private static final Logger log = Logger.getLogger("sns");
    private OpvGeneralService opvGeneralService;
    private OpviewNotifyService opviewNotifyService;
    private ImportConfig config;
    SimpleDateFormat simpleDateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public SimpleNotificationService(ImportConfig config, OpviewNotifyService opviewNotifyService) {
        this.config=config;
        this.opvGeneralService = opvGeneralService;
        this.opviewNotifyService = opviewNotifyService;
    }


    public void receiveSNS(String responseJSON, String host) {

        //解析json
        try {
            //現在時間(設為收到訊息的時間)
            Calendar now = Calendar.getInstance();
            Date receivingTime = now.getTime();

            Gson gson = new Gson();
            //responseJSON轉換成Gson物件
            SnsResponseJSON gsonStaff = gson.fromJson(responseJSON, SnsResponseJSON.class);


            SendMethods sendMethods = new SendMethods(config);

            //設置時區UTC
            simpleDateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
            //轉當地時區時間

            //根據SNS的訊息類型進行處理
            if (gsonStaff.getNotificationType().equals("Delivery")) {//接收成功
                log.info("Delivery!");

                //將回傳訊息解析成DB的LogEntity
                MailSnsLogEntity mailSnsLogEntity = getMailSnsLog(gsonStaff);
                String utcTime = gsonStaff.getDelivery().getTimestamp();
                //時間從世界時區轉成當地時區
                Date snsResponseTime = simpleDateFormatUTC.parse(utcTime);
                mailSnsLogEntity.setSnsResponseTime(new Timestamp(snsResponseTime.getTime()));
                mailSnsLogEntity.setReceivingTime(new Timestamp(receivingTime.getTime()));
                mailSnsLogEntity.setHost(host);

                //寫入Log

                opviewNotifyService.insertMailSnsLog(mailSnsLogEntity);

            } else if (gsonStaff.getNotificationType().equals("Bounce")) {//反彈
                log.warn("Bounce!");


                //將回傳訊息解析成DB的LogEntity
                MailSnsLogEntity mailSnsLogEntity = getMailSnsLog(gsonStaff);
                String utcTime = gsonStaff.getBounce().getTimestamp();
                //時間從世界時區轉成當地時區
                Date snsResponseTime = simpleDateFormatUTC.parse(utcTime);
                mailSnsLogEntity.setSnsResponseTime(new Timestamp(snsResponseTime.getTime()));
                mailSnsLogEntity.setReceivingTime(new Timestamp(receivingTime.getTime()));
                mailSnsLogEntity.setHost(host);
                //寫入LOG
                opviewNotifyService.insertMailSnsLog(mailSnsLogEntity);

                if (gsonStaff.getBounce().getBounceType().equals("Transient") && gsonStaff.getBounce().getBounceSubType().equals("General")) {
                    //不加入黑名單
                    log.info("bounceType : Transient , bounceSubType : General. Don't add blacklist.");

                } else {

                    AmazonSES amazonSES = new AmazonSES( config,opvGeneralService,opviewNotifyService);
                    AmazonSESv2 amazonSESv2 = new AmazonSESv2(config,opvGeneralService,opviewNotifyService);
                    for (BouncedRecipient bouncedRecipient : gsonStaff.getBounce().getBouncedRecipients()) {
                        //將信箱加入即時黑名單
                        amazonSES.addBlacklist(mailSnsLogEntity.getToMail());
                        amazonSESv2.addBlacklist(mailSnsLogEntity.getToMail());
                        //將信箱加入黑名單(DB)
                        opvGeneralService.insertMailBlacklist(mailSnsLogEntity.getToMail(), "Bounce", responseJSON, bouncedRecipient.getStatus(),
                                bouncedRecipient.getDiagnosticCode());
                    }
                    //發送Telegram訊息
                    sendMethods.sendByTelegram("SNS Message - Bounce \n\n" + mailSnsLogEntity.getSubject() + "\n" + mailSnsLogEntity.getToMail());
                    //發送信件給備用信箱，降低失敗率
                    amazonSES.sendMailReduceFailRateBySES("0", "SNS_Message", "SNS Message - Bounce <BR>Please check log.");
                }


            } else if (gsonStaff.getNotificationType().equals("Complaint")) {//抱怨
                log.warn("Complaint!");


                //將回傳訊息解析成DB的LogEntity
                MailSnsLogEntity mailSnsLogEntity = getMailSnsLog(gsonStaff);
                String utcTime = gsonStaff.getComplaint().getTimestamp();
                //時間從世界時區轉成當地時區
                Date snsResponseTime = simpleDateFormatUTC.parse(utcTime);
                mailSnsLogEntity.setSnsResponseTime(new Timestamp(snsResponseTime.getTime()));
                mailSnsLogEntity.setReceivingTime(new Timestamp(receivingTime.getTime()));
                mailSnsLogEntity.setHost(host);
                //寫入LOG
                opviewNotifyService.insertMailSnsLog(mailSnsLogEntity);

                AmazonSES amazonSES = new AmazonSES(config,opvGeneralService,opviewNotifyService);
                AmazonSESv2 amazonSESv2 = new AmazonSESv2(config,opvGeneralService,opviewNotifyService);
                //將信箱加入即時黑名單
                amazonSES.addBlacklist(mailSnsLogEntity.getToMail());
                amazonSESv2.addBlacklist(mailSnsLogEntity.getToMail());
                //將信箱加入黑名單(DB)
                opvGeneralService.insertMailBlacklist(mailSnsLogEntity.getToMail(), "Complaint", responseJSON, null, null);

                //從ComplainedRecipient物件取信箱
//                for (ComplainedRecipient complainedRecipient : gsonStaff.getComplaint().getComplainedRecipients()) {
//                    //將信箱加入即時黑名單
//                    amazonSES.addBlacklist(complainedRecipient.getEmailAddress());
//                    amazonSESv2.addBlacklist(complainedRecipient.getEmailAddress());
//                    //將信箱加入黑名單(DB)
//                    mailBlackListDAO.insertMailBlacklist(complainedRecipient.getEmailAddress(), "Complaint", responseJSON,
//                            null, null);
//
//                }

                //發送Telegram訊息
                sendMethods.sendByTelegram("SNS Message - Complaint \n\n" + mailSnsLogEntity.getSubject() + "\n" + mailSnsLogEntity.getToMail());
                //發送信件給備用信箱，降低失敗率
                amazonSES.sendMailReduceFailRateBySES("0", "SNS_Message", "SNS Message - Complaint <BR>Please check log.");

            } else {//例外狀況(不屬於以上的NotificationType)
                log.warn("Exception!");
                //發送Telegram訊息
                sendMethods.sendByTelegram("Mail API - Unknown SNS Message.\nPlease check log.");

                //發送信件通知維運人員(非aws)
                //sendMethods.sendByMail("1", "Mail API - Unknown SNS Message", responseJSON);
            }


        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }



    }



    public MailSnsLogEntity getMailSnsLog(SnsResponseJSON gsonStaff) {
        MailSnsLogEntity mailSnsLogEntity = new MailSnsLogEntity();

        try {

            mailSnsLogEntity.setSubject(gsonStaff.getMail().getCommonHeaders().getSubject());
            mailSnsLogEntity.setStatus(gsonStaff.getNotificationType());

            String toMail = "";
            if (gsonStaff.getMail().getCommonHeaders().getTo().size() > 0) {
                toMail += gsonStaff.getMail().getCommonHeaders().getTo().get(0);
                int i = 1;
                while (i < gsonStaff.getMail().getCommonHeaders().getTo().size()) {
                    toMail += "," + gsonStaff.getMail().getCommonHeaders().getTo().get(i);
                    i++;
                }
            }
            mailSnsLogEntity.setToMail(toMail);

            String fromMail = "";
            if (gsonStaff.getMail().getCommonHeaders().getFrom().size() > 0) {
                fromMail += gsonStaff.getMail().getCommonHeaders().getFrom().get(0).replaceAll("<[-a-zA-Z0-9_]+@[a-zA-Z0-9\\._]+>", "");
                int i = 1;
                while (i < gsonStaff.getMail().getCommonHeaders().getFrom().size()) {
                    fromMail += "," + gsonStaff.getMail().getCommonHeaders().getFrom().get(i);
                    i++;
                }
            }
            mailSnsLogEntity.setFromMail(fromMail);

            //轉換時間字串成Timestamp格式
            String utcTime = gsonStaff.getMail().getTimestamp();
            //設置時區UTC
            simpleDateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
            //轉當地時區時間
            Date sendTime = simpleDateFormatUTC.parse(utcTime);

            mailSnsLogEntity.setSendTime(new Timestamp(sendTime.getTime()));
            //mailSnsLogEntity.setSnsResponseTime(now);
            mailSnsLogEntity.setSource(gsonStaff.getMail().getSource());
            mailSnsLogEntity.setSourceIp(gsonStaff.getMail().getSourceIp());

            mailSnsLogEntity.setMessageId(gsonStaff.getMail().getMessageId());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return mailSnsLogEntity;

    }



}
