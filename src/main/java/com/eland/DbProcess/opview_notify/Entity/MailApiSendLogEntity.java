package com.eland.DbProcess.opview_notify.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mail_api_send_log", schema = "dbo", catalog = "opview_notify")
public class MailApiSendLogEntity {
    private int id;
    private String pid;
    private String sender;
    private String senderMail;
    private Timestamp createTime;
    private Timestamp endTime;
    private String status;
    private String senderIp;
    private String subject;
    private String toMail;
    private String attachment;
    private String responseJson;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pid")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "sender")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "sender_mail")
    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "sender_ip")
    public String getSenderIp() {
        return senderIp;
    }

    public void setSenderIp(String senderIp) {
        this.senderIp = senderIp;
    }

    @Basic
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "to_mail")
    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    @Basic
    @Column(name = "attachment")
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Basic
    @Column(name = "response_json")
    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailApiSendLogEntity that = (MailApiSendLogEntity) o;
        return id == that.id &&
                Objects.equals(pid, that.pid) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(senderMail, that.senderMail) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(senderIp, that.senderIp) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(toMail, that.toMail) &&
                Objects.equals(attachment, that.attachment) &&
                Objects.equals(responseJson, that.responseJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pid, sender, senderMail, createTime, endTime, status, senderIp, subject, toMail, attachment, responseJson);
    }
}
