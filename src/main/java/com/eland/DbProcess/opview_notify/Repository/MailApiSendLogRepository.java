package com.eland.DbProcess.opview_notify.Repository;

import com.eland.DbProcess.opview_notify.Entity.MailApiSendLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailApiSendLogRepository extends JpaRepository<MailApiSendLogEntity, Long> {
    List<MailApiSendLogEntity> findAllByPid(String pid);

}