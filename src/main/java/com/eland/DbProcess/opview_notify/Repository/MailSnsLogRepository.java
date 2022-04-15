package com.eland.DbProcess.opview_notify.Repository;


import com.eland.DbProcess.opview_notify.Entity.MailSnsLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MailSnsLogRepository extends JpaRepository<MailSnsLogEntity, Long> {


}