package com.eland.DbProcess.opv_general.Repository;

import com.eland.DbProcess.opv_general.Entity.MailGroupNotificationMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailGroupNotificationMappingRepository extends JpaRepository<MailGroupNotificationMappingEntity, Long> {

List<MailGroupNotificationMappingEntity> findAllByGroupId(int groupId);
}