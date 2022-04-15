package com.eland.SnsJSON;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccyang on 2019/7/12.
 */
public class Mail {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sourceArn")
    @Expose
    private String sourceArn;
    @SerializedName("sourceIp")
    @Expose
    private String sourceIp;
    @SerializedName("sendingAccountId")
    @Expose
    private String sendingAccountId;
    @SerializedName("messageId")
    @Expose
    private String messageId;
    @SerializedName("destination")
    @Expose
    private List<String> destination = null;
    @SerializedName("headersTruncated")
    @Expose
    private Boolean headersTruncated;
    @SerializedName("headers")
    @Expose
    private List<Header> headers = null;
    @SerializedName("commonHeaders")
    @Expose
    private CommonHeaders commonHeaders;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceArn() {
        return sourceArn;
    }

    public void setSourceArn(String sourceArn) {
        this.sourceArn = sourceArn;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getSendingAccountId() {
        return sendingAccountId;
    }

    public void setSendingAccountId(String sendingAccountId) {
        this.sendingAccountId = sendingAccountId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public List<String> getDestination() {
        return destination;
    }

    public void setDestination(List<String> destination) {
        this.destination = destination;
    }

    public Boolean getHeadersTruncated() {
        return headersTruncated;
    }

    public void setHeadersTruncated(Boolean headersTruncated) {
        this.headersTruncated = headersTruncated;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public CommonHeaders getCommonHeaders() {
        return commonHeaders;
    }

    public void setCommonHeaders(CommonHeaders commonHeaders) {
        this.commonHeaders = commonHeaders;
    }

}
