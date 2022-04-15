package com.eland.SnsJSON;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ccyang on 2019/7/12.
 */
public class BouncedRecipient {

    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("diagnosticCode")
    @Expose
    private String diagnosticCode;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    public void setDiagnosticCode(String diagnosticCode) {
        this.diagnosticCode = diagnosticCode;
    }

}
