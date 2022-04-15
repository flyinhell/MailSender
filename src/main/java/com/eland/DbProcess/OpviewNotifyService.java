package com.eland.DbProcess;

import com.eland.DbProcess.opv_general.Entity.VwMailGroupMappingEntity;
import com.eland.DbProcess.opview_notify.Entity.*;
import com.eland.DbProcess.opview_notify.Repository.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpviewNotifyService {

    private static final Logger log = Logger.getLogger("sns");

    @Autowired
    private MailApiSendLogRepository mailApiSendLogRepository;
    @Autowired
    private MailSnsLogRepository mailSnsLogRepository;

    /**
     *
     * @param mailApiSendLogEntity
     */
    public void insertMailApiSendLog(MailApiSendLogEntity mailApiSendLogEntity) {
        try {
            mailApiSendLogRepository.save(mailApiSendLogEntity);
        } catch (Exception e) {
            log.error("OpviewNotifyService.insertMailApiSendLog 發生錯誤： " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param pid
     * @return
     */
    public MailApiSendLogEntity getMailApiSendLogEntity(String pid) {
        MailApiSendLogEntity mailApiSendLogEntity=null;
        try {
            mailApiSendLogEntity = mailApiSendLogRepository.findAllByPid(pid).get(0);
        } catch (Exception e) {
            log.error("OpviewNotifyService.getMailApiSendLogEntity 發生錯誤： " + e.getMessage(), e);
        }
        return mailApiSendLogEntity;
    }

    public void insertMailSnsLog(MailSnsLogEntity mailSnsLogEntity){
        try {
            mailSnsLogRepository.save(mailSnsLogEntity);
        } catch (Exception e) {
            log.error("OpviewNotifyService.insertMailSnsLog 發生錯誤： " + e.getMessage(), e);
        }
    }



}
