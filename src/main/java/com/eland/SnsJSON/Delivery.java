package com.eland.SnsJSON;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccyang on 2019/7/12.
 */
public class Delivery {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("processingTimeMillis")
    @Expose
    private Integer processingTimeMillis;
    @SerializedName("recipients")
    @Expose
    private List<String> recipients = null;
    @SerializedName("smtpResponse")
    @Expose
    private String smtpResponse;
    @SerializedName("remoteMtaIp")
    @Expose
    private String remoteMtaIp;
    @SerializedName("reportingMTA")
    @Expose
    private String reportingMTA;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getProcessingTimeMillis() {
        return processingTimeMillis;
    }

    public void setProcessingTimeMillis(Integer processingTimeMillis) {
        this.processingTimeMillis = processingTimeMillis;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getSmtpResponse() {
        return smtpResponse;
    }

    public void setSmtpResponse(String smtpResponse) {
        this.smtpResponse = smtpResponse;
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
