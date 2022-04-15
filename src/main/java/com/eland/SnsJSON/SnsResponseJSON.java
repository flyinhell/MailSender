package com.eland.SnsJSON;

/**
 * Created by ccyang on 2019/7/12.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SnsResponseJSON {

    @SerializedName("notificationType")
    @Expose
    private String notificationType;
    @SerializedName("bounce")
    @Expose
    private Bounce bounce;
    @SerializedName("complaint")
    @Expose
    private Complaint complaint;
    @SerializedName("delivery")
    @Expose
    private Delivery delivery;
    @SerializedName("mail")
    @Expose
    private Mail mail;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Bounce getBounce() {
        return bounce;
    }

    public void setBounce(Bounce bounce) {
        this.bounce = bounce;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

}