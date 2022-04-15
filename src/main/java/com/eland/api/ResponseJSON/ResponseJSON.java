package com.eland.api.ResponseJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccyang on 2019/6/21.
 */
public class ResponseJSON {

    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("mail")
    @Expose
    private List<String> mail = null;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("error_code")
    @Expose
    private String errorCode;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMail() {
        return mail;
    }

    public void setMail(List<String> mail) {
        this.mail = mail;
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
