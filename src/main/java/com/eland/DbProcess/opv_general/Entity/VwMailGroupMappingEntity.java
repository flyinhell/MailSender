package com.eland.DbProcess.opv_general.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vw_mail_group_mapping", schema = "dbo", catalog = "opv_general")
public class VwMailGroupMappingEntity {
    private String id;
    private int groupId;
    private String mailAddress;

    @Id
    @Basic
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    @Column(name = "mail_address")
    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VwMailGroupMappingEntity that = (VwMailGroupMappingEntity) o;
        return groupId == that.groupId &&
                Objects.equals(id, that.id) &&
                Objects.equals(mailAddress, that.mailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, mailAddress);
    }
}
