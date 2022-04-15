package com.eland.SnsJSON;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccyang on 2019/7/12.
 */
public class Complaint {

    @SerializedName("complainedRecipients")
    @Expose
    private List<ComplainedRecipient> complainedRecipients = null;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("feedbackId")
    @Expose
    private String feedbackId;
    @SerializedName("userAgent")
    @Expose
    private String userAgent;
    @SerializedName("complaintFeedbackType")
    @Expose
    private String complaintFeedbackType;

    public List<ComplainedRecipient> getComplainedRecipients() {
        return complainedRecipients;
    }

    public void setComplainedRecipients(List<ComplainedRecipient> complainedRecipients) {
        this.complainedRecipients = complainedRecipients;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getComplaintFeedbackType() {
        return complaintFeedbackType;
    }

    public void setComplaintFeedbackType(String complaintFeedbackType) {
        this.complaintFeedbackType = complaintFeedbackType;
    }

}
