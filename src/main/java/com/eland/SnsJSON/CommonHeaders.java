package com.eland.SnsJSON;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccyang on 2019/7/12.
 */
public class CommonHeaders {

    @SerializedName("from")
    @Expose
    private List<String> from = null;
    @SerializedName("to")
    @Expose
    private List<String> to = null;
    @SerializedName("messageId")
    @Expose
    private String messageId;
    @SerializedName("subject")
    @Expose
    private String subject;

    public List<String> getFrom() {
        return from;
    }

    public void setFrom(List<String> from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
