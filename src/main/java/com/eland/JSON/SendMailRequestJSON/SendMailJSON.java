package com.eland.JSON.SendMailRequestJSON;

/**
 * Created by ccyang on 2020/3/19.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SendMailJSON {

    @SerializedName("Sender")
    @Expose
    private String sender;
    @SerializedName("SenderMail")
    @Expose
    private String senderMail;
    @SerializedName("RecipientEmail")
    @Expose
    private List<String> RecipientEmail = null;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("Attachment")
    @Expose
    private List<Attachment> attachment = null;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public List<String> getRecipientEmail() {
        return RecipientEmail;
    }

    public void setRecipientEmail(List<String> RecipientEmail) {
        this.RecipientEmail = RecipientEmail;
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

    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

}