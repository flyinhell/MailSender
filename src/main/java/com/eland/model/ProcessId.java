package com.eland.model;

import java.util.Calendar;


public class ProcessId {
    private String id;
    private int subId = 0;
    private String apiType;
    private String queryIp;
    private String requestJson;
    private String model;
    private String errorCode = "0";
    private String errorMessage = "OK";
    private String errorMessageDetail ;
    private Calendar queryStartTime;
    private Calendar queryEndTime;
    private Calendar queryTime;


    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public String getQueryIp() {
        return queryIp;
    }

    public void setQueryIp(String queryIp) {
        this.queryIp = queryIp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Calendar getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(Calendar queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public void setQueryEndTime() {
        Calendar end = Calendar.getInstance();
        this.queryEndTime = end;
    }

    public Calendar getQueryStartTime() {
        return queryStartTime;
    }

    public void setQueryStartTime(Calendar queryStartTime) {
        this.queryStartTime = queryStartTime;
    }

    public Calendar getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Calendar queryTime) {
        this.queryTime = queryTime;
    }

    public String getErrorMessageDetail() {
        return errorMessageDetail;
    }

    public void setErrorMessageDetail(String errorMessageDetail) {
        this.errorMessageDetail = errorMessageDetail;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public void updateSubId() {
        this.subId++;
    }



    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }
}
