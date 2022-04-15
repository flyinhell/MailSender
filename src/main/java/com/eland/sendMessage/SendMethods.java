package com.eland.sendMessage;

import com.eland.DbProcess.OpvGeneralService;
import com.eland.DbProcess.opv_general.Entity.MailGroupNotificationMappingEntity;
import com.eland.api.ResponseJSON.ResponseJSON;
import com.eland.tools.ImportConfig;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ccyang on 2017/11/13.
 */
public class SendMethods {
    private static Logger log = Logger.getLogger("Log");
    private ImportConfig config;
    private OpvGeneralService opvGeneralService;
    public SendMethods(OpvGeneralService opvGeneralService) {
        this.opvGeneralService=opvGeneralService;
    }
    public SendMethods(ImportConfig config){
        try {
            this.config=config;
        } catch (Exception e) {

        }
    }
    public String sendByMail(String groupId, String titleText, String contentText) {
        String json = "";
        try {
            Gson gson = new Gson();
            String errorMessage = "OK";
            String errorCode = "0";

            ResponseJSON responseJSON = new ResponseJSON();
            if (groupId == null||groupId.length()<1) {
                errorMessage = "Group id is null.";
                errorCode = "2.1";
            } else if (titleText == null||titleText.length()<1) {
                errorMessage = "Title is null.";
                errorCode = "2.2";
            } else if (contentText == null||contentText.length()<1) {
                errorMessage = "Content is null.";
                errorCode = "2.3";
            } else {

                //取得發信名單
                LinkedList<String> mailTargetList = new LinkedList<String>();


                List<MailGroupNotificationMappingEntity> mailGroupNotificationMappingDAOMailList = opvGeneralService
                        .getEmail(groupId);
                for (MailGroupNotificationMappingEntity mailGroupNotificationMappingEntity : mailGroupNotificationMappingDAOMailList) {
                    mailTargetList.add(mailGroupNotificationMappingEntity.getMail());
                }

                if (mailTargetList.size() > 0) {
                    responseJSON.setGroupId(groupId);
                    responseJSON.setTitle(titleText);
                    responseJSON.setContent(contentText);
                    responseJSON.setMail(mailTargetList);

                    //向審核者(auditor)發信通知
                    for (String mailTarget : mailTargetList) {
                        HashMap<String, String> auditorMailMap = new HashMap<String, String>();
                        auditorMailMap.put(mailTarget, "");
                        MailSender mailSender = new MailSender(config);
                        MailInfo mailInfo = new MailInfo();
                        mailInfo.setToEmails(auditorMailMap);
                        mailInfo.setSubject(titleText);
                        mailInfo.setContent(contentText);
                        if (mailSender.sendMail(mailInfo)) {
                            log.info("Send mail successfully. - " + mailTarget);
                        } else {
                            log.error("Send mail error - "+mailTarget);
                            errorMessage = "Send mail error!" ;
                            errorCode = "1.1";
                        }
                    }
                } else {
                    errorMessage = "Not find e-mail list.";
                    errorCode = "3.1";
                }
            }
            responseJSON.setErrorMessage(errorMessage);
            responseJSON.setErrorCode(errorCode);
            json = gson.toJson(responseJSON);
            log.info("ResponseJSON : " + json);


        } catch (Exception e) {
            log.error(e.getMessage(),e);
            log.error("Send mail error!");
            json=" {\"error_message\":\"Send mail error!\",\"error_code\":\"1.0\"}";
        }

        return json;
    }



    public static void sendByGMail() {
        String host = "smtp.gmail.com";
        int port = 587;
        final String username = "TEST@gmail.com";
        final String password = "TEST";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }} );

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("TEST@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                                  InternetAddress.parse("jimmychang@eland.com.tw"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,\n\n No spam to my email, please!");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, username, password);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendByTelegram(String Message) {
        StringBuffer responseJSON = new StringBuffer("");
        try {
            URL urlObj = new URL("https://api.telegram.org/bot818668606:AAFCxp95h-7eZsxAIzZeS_WjK-dcThQGR4g/sendMessage?");
            //取得 URLConnection
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

            conn.setDoInput(true);  //設定為可從伺服器讀取資料
            conn.setDoOutput(true);  //設定為可寫入資料至伺服器

            conn.setRequestMethod("POST");  //設定請求方式為POST

            //以下是設定 MIME 標頭中的 Content-type
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            conn.connect();  //開始連接

            //透過 URLConnection 的 getOutputStream() 取的 OutputStream, 並建立以UTF-8 為編碼的 OutputStreamWriter
            PrintWriter printWr = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"), true);
            //阿低微運
//            printWr.print("chat_id=-1001251565260&text="+ URLEncoder.encode(Message, "UTF-8"));
            //rd5test
            printWr.print("chat_id=-1001190165016&text="+ URLEncoder.encode(Message, "UTF-8"));
            printWr.flush();   //清除緩衝區或關閉

            String inputLine = "";
            //利用 URLConnection 的 getInputStream() 取得 InputStream
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            while ((inputLine = reader.readLine()) != null) {
                responseJSON.append(inputLine);
            }
            //logger.debug(sb);  //印出結果
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
