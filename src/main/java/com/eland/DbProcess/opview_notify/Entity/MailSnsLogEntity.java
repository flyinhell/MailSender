package com.eland.DbProcess.opview_notify.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mail_sns_log", schema = "dbo", catalog = "opview_notify")
public class MailSnsLogEntity {
    private int id;
    private String subject;
    private String status;
    private String toMail;
    private String fromMail;
    private Timestamp receivingTime;
    private Timestamp sendTime;
    private Timestamp snsResponseTime;
    private String source;
    private String sourceIp;
    private String host;
    private String messageId;

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
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
    @Column(name = "to_mail")
    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    @Basic
    @Column(name = "from_mail")
    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    @Basic
    @Column(name = "receiving_time")
    public Timestamp getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(Timestamp receivingTime) {
        this.receivingTime = receivingTime;
    }

    @Basic
    @Column(name = "send_time")
    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "sns_response_time")
    public Timestamp getSnsResponseTime() {
        return snsResponseTime;
    }

    public void setSnsResponseTime(Timestamp snsResponseTime) {
        this.snsResponseTime = snsResponseTime;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "source_ip")
    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    @Basic
    @Column(name = "host")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Basic
    @Column(name = "message_id")
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailSnsLogEntity that = (MailSnsLogEntity) o;
        return id == that.id &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(status, that.status) &&
                Objects.equals(toMail, that.toMail) &&
                Objects.equals(fromMail, that.fromMail) &&
                Objects.equals(receivingTime, that.receivingTime) &&
                Objects.equals(sendTime, that.sendTime) &&
                Objects.equals(snsResponseTime, that.snsResponseTime) &&
                Objects.equals(source, that.source) &&
                Objects.equals(sourceIp, that.sourceIp) &&
                Objects.equals(host, that.host) &&
                Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, status, toMail, fromMail, receivingTime, sendTime, snsResponseTime, source, sourceIp, host, messageId);
    }
}
