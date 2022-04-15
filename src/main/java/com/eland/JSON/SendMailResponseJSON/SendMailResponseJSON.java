package com.eland.JSON.SendMailResponseJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccyang on 2020/3/20.
 */
public class SendMailResponseJSON {

    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("send_mail")
    @Expose
    private List<String> sendMail = null;
    @SerializedName("fail_mail")
    @Expose
    private List<String> failMail = null;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("error_code")
    @Expose
    private String errorCode;

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

    public List<String> getSendMail() {
        return sendMail;
    }

    public void setSendMail(List<String> sendMail) {
        this.sendMail = sendMail;
    }

    public List<String> getFailMail() {
        return failMail;
    }

    public void setFailMail(List<String> failMail) {
        this.failMail = failMail;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
