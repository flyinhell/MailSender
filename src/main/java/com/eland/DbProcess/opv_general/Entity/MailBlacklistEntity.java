package com.eland.DbProcess.opv_general.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mail_blacklist", schema = "dbo", catalog = "opv_general")
public class MailBlacklistEntity {
    private int id;
    private String mailAddress;
    private Timestamp createTime;
    private String description;
    private String responseJson;
    private String status;
    private String diagnosticCode;

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
    @Column(name = "mail_address")
    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "response_json")
    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
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
    @Column(name = "diagnostic_code")
    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    public void setDiagnosticCode(String diagnosticCode) {
        this.diagnosticCode = diagnosticCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailBlacklistEntity that = (MailBlacklistEntity) o;
        return id == that.id &&
                Objects.equals(mailAddress, that.mailAddress) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(description, that.description) &&
                Objects.equals(responseJson, that.responseJson) &&
                Objects.equals(status, that.status) &&
                Objects.equals(diagnosticCode, that.diagnosticCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mailAddress, createTime, description, responseJson, status, diagnosticCode);
    }
}
