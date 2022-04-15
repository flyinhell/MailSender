package com.eland.aws.model;

import com.eland.JSON.SendMailRequestJSON.Attachment;
import com.eland.tools.SslUtils;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class SendMail {
    private static final Logger awsLog = Logger.getLogger("aws");

    private static String awsMailServer;
    private static String awsMailProtocol;
    private static String awsSmtpUserName;
    private static String awsSmtpPassword;
    private static String awsFromMail;
    private static String awsFromName;
    private static String attachmentFilePath;

    public SendMail(String awsMailServer, String awsMailProtocol, String awsSmtpUserName, String awsSmtpPassword, String awsFromName, String awsFromMail, String attachmentFilePath) {

        this.awsMailServer = awsMailServer;
        this.awsMailProtocol = awsMailProtocol;
        this.awsSmtpUserName = awsSmtpUserName;
        this.awsSmtpPassword = awsSmtpPassword;
        this.awsFromName = awsFromName;
        this.awsFromMail = awsFromMail;
        this.attachmentFilePath = attachmentFilePath;

    }

    public Boolean send(String mailTarget, String subject, String body) {
        awsLog.info("Trying Send Mail to " + mailTarget);
        Boolean result = false;
        Transport transport = null;
        try {
            // Create a Properties object to contain connection configuration information.
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", awsMailProtocol);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.trust", awsMailServer);
            //    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            // Create a Session object to represent a mail session with the specified properties.
            Session session = Session.getDefaultInstance(props);

            // Create a message with the specified information.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(awsFromMail, awsFromName));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTarget));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(body, "text/html;charset=UTF-8");

            // Add a configuration set header. Comment or delete the
            // next line if you are not using a configuration set
            //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            // Create a transport.
            transport = session.getTransport();
            // Send the message.

            // Connect to Amazon SES using the SMTP username and password you specified above.
            SslUtils.ignoreSsl();
            transport.connect(awsMailServer, awsSmtpUserName, awsSmtpPassword);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            awsLog.info(mailTarget + " - Email sent!");
            result = true;

        } catch (Exception e) {
            awsLog.error("The email was not sent.");
            awsLog.error("Error message: " + e.getMessage(), e);
        } finally {
            // Close and terminate the connection.
            try {
                transport.close();

            } catch (Exception e) {
                awsLog.error(" Transport Close Error : " + e.getMessage(), e);
            }
        }
        awsLog.info(result);

        return result;
    }

    public Boolean send(String mailTarget, String subject, String body, List<Attachment> attachmentList) {

        Boolean result = false;
        Transport transport = null;
        try {
            // Create a Properties object to contain connection configuration information.
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", awsMailProtocol);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.trust", awsMailServer);

            // Create a Session object to represent a mail session with the specified properties.
            Session session = Session.getDefaultInstance(props);

            // Create a message with the specified information.
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(awsFromMail, awsFromName));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTarget));
            msg.setSubject(subject);
//          msg.setSubject(MimeUtility.encodeWord(subject, "UTF-8", "Q")); //utf轉碼

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            //        messageBodyPart.setText(body);(html失效)
            messageBodyPart.setContent(body, "text/html;charset=UTF-8");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            for (Attachment attachment : attachmentList) {
                //加入檔案至附件
                messageBodyPart = new MimeBodyPart();
                String path = attachmentFilePath + attachment.getAttachmentName();

                FileDataSource source = new FileDataSource(path);
                messageBodyPart.setDataHandler(new DataHandler(source));
                String fileName = attachment.getAttachmentName();
                //  awsLog.info("fileName:" + fileName);
                //  messageBodyPart.setFileName(MimeUtility.encodeWord(fileName, "UTF-8", "Q"));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
            }


            // Send the complete message parts
            msg.setContent(multipart);
            // Send message
            //Transport.send(msg);

            // Add a configuration set header. Comment or delete the
            // next line if you are not using a configuration set
            //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

            // Create a transport.
            transport = session.getTransport();

            // Send the message.

            awsLog.info(mailTarget + " - Sending...");
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(awsMailServer, awsSmtpUserName, awsSmtpPassword);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            awsLog.info(mailTarget + " - Email sent!");
            result = true;
        } catch (Exception e) {
            awsLog.error("The email was not sent.");
            awsLog.error("Error message: " + e.getMessage(), e);
        } finally {
            // Close and terminate the connection.
            try {
                transport.close();
            } catch (Exception e) {
                awsLog.error(" Transport Close Error : " + e.getMessage(), e);
            }
        }
        return result;
    }
}
