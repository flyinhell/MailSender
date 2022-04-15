package com.eland.DbProcess.opv_general.Repository;
import com.eland.DbProcess.opv_general.Entity.MailBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailBlackListRepository extends JpaRepository<MailBlacklistEntity, Long> {


}