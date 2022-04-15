package com.eland.sendMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 郵件資訊
 *
 * Created by danielhsieh on 2014/2/12.
 */
public class MailInfo {
    private String mailServer;
    private String mailProtocol;
    private String fromMailAddress; //寄件人郵件位置
    private String fromName;    //寄件人名稱
    private Map<String, String> toEmails = new HashMap<String, String>();   //一般收件人
    private Map<String, String> ccEmails = new HashMap<String, String>();   //副本收件人
    private Map<String, String> bccEmails = new HashMap<String, String>();  //密件副本收件人
    private String subject; //主旨
    private String content; //內文
    private List<String> attachmentFilePaths;   //附件檔案路徑
    private String runTime; //發信時間 ex:"07:00:00"

    public String getMailServer() {
        return mailServer;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    public String getMailProtocol() {
        return mailProtocol;
    }

    public void setMailProtocol(String mailProtocol) {
        this.mailProtocol = mailProtocol;
    }

    public String getFromMailAddress() {
        return fromMailAddress;
    }

    public void setFromMailAddress(String fromMailAddress) {
        this.fromMailAddress = fromMailAddress;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Map<String, String> getToEmails() {
        if (this.toEmails == null) {
            this.toEmails = new HashMap<String, String>();
        }
        return toEmails;
    }

    public void setToEmails(Map<String, String> toEmails) {
        this.toEmails = toEmails;
    }

    public void addToEmails(String emailAddress, String name) {
        this.toEmails.put(emailAddress, name);
    }

    public Map<String, String> getCcEmails() {
        if (this.ccEmails == null) {
            this.ccEmails = new HashMap<String, String>();
        }
        return ccEmails;
    }

    public void setCcEmails(Map<String, String> ccEmails) {
        this.ccEmails = ccEmails;
    }

    public void addCcEmails(String emailAddress, String name) {
        this.ccEmails.put(emailAddress, name);
    }

    public Map<String, String> getBccEmails() {
        if (this.bccEmails == null) {
            this.bccEmails = new HashMap<String, String>();
        }
        return bccEmails;
    }

    public void setBccEmails(Map<String, String> bccEmails) {
        this.bccEmails = bccEmails;
    }

    public void addBccEmails(String emailAddress, String name) {
        this.bccEmails.put(emailAddress, name);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAttachmentFilePaths() {
        if (attachmentFilePaths == null) {
            attachmentFilePaths = new ArrayList<String>();
        }
        return attachmentFilePaths;
    }

    public void setAttachmentFilePaths(List<String> attachmentFilePaths) {
        this.attachmentFilePaths = attachmentFilePaths;
    }

    public void addAttachmentFilePaths(String filePath) {
        getAttachmentFilePaths().add(filePath);
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }
}
