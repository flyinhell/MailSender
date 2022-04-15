package com.eland.SnsJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccyang on 2019/7/12.
 */
public class Bounce {

    @SerializedName("bounceType")
    @Expose
    private String bounceType;
    @SerializedName("bounceSubType")
    @Expose
    private String bounceSubType;
    @SerializedName("bouncedRecipients")
    @Expose
    private List<BouncedRecipient> bouncedRecipients = null;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("feedbackId")
    @Expose
    private String feedbackId;
    @SerializedName("remoteMtaIp")
    @Expose
    private String remoteMtaIp;
    @SerializedName("reportingMTA")
    @Expose
    private String reportingMTA;

    public String getBounceType() {
        return bounceType;
    }

    public void setBounceType(String bounceType) {
        this.bounceType = bounceType;
    }

    public String getBounceSubType() {
        return bounceSubType;
    }

    public void setBounceSubType(String bounceSubType) {
        this.bounceSubType = bounceSubType;
    }

    public List<BouncedRecipient> getBouncedRecipients() {
        return bouncedRecipients;
    }

    public void setBouncedRecipients(List<BouncedRecipient> bouncedRecipients) {
        this.bouncedRecipients = bouncedRecipients;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getRemoteMtaIp() {
        return remoteMtaIp;
    }

    public void setRemoteMtaIp(String remoteMtaIp) {
        this.remoteMtaIp = remoteMtaIp;
    }

    public String getReportingMTA() {
        return reportingMTA;
    }

    public void setReportingMTA(String reportingMTA) {
        this.reportingMTA = reportingMTA;
    }

}
