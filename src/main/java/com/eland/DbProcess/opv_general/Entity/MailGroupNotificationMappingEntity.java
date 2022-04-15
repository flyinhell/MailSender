package com.eland.DbProcess.opv_general.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mail_group_notification_mapping", schema = "dbo", catalog = "opv_general")
public class MailGroupNotificationMappingEntity {
    private int id;
    private int groupId;
    private String mail;
    private int blacklistSwitch;


    @Id
    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "blacklist_switch")
    public int getBlacklistSwitch() {
        return blacklistSwitch;
    }

    public void setBlacklistSwitch(int blacklistSwitch) {
        this.blacklistSwitch = blacklistSwitch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailGroupNotificationMappingEntity that = (MailGroupNotificationMappingEntity) o;
        return id == that.id &&
                groupId == that.groupId &&
                blacklistSwitch == that.blacklistSwitch &&
                Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, mail, blacklistSwitch);
    }
}
