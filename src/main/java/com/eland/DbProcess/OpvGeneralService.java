package com.eland.DbProcess;

import com.eland.DbProcess.opv_general.Entity.MailBlacklistEntity;
import com.eland.DbProcess.opv_general.Entity.MailGroupNotificationMappingEntity;
import com.eland.DbProcess.opv_general.Entity.VwMailGroupMappingEntity;
import com.eland.DbProcess.opv_general.Repository.MailBlackListRepository;
import com.eland.DbProcess.opv_general.Repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OpvGeneralService {
    private static final Logger log = Logger.getLogger("sns");
    @Autowired
    private MailBlackListRepository mailBlackListRepository;
    @Autowired
    private VwMailGroupMappingRepository vwMailGroupMappingRepository;
    @Autowired
    private MailGroupNotificationMappingRepository mailGroupNotificationMappingRepository;

    /**
     * 取得完整黑名單
     *
     * @return
     */
    public Set<String> getMailBlacklist() {
        Set<String> mailBlackList = new HashSet<String>();
        try {
            List<MailBlacklistEntity> mailBlacklistEntityList = mailBlackListRepository.findAll();
            for (MailBlacklistEntity mailBlacklistEntity : mailBlacklistEntityList) {
                mailBlackList.add(mailBlacklistEntity.getMailAddress());
            }
        } catch (Exception e) {
            log.error("OpvGeneralService.getMailBlacklist 發生錯誤： " + e.getMessage(), e);
        }
        return mailBlackList;
    }

    /**
     *
     */
    public List<VwMailGroupMappingEntity> getEmailByGroupId(String groupId) {
        List<VwMailGroupMappingEntity> vwMailGroupMappingEntityList = null;
        try {
            int intGroupId = Integer.parseInt(groupId);
           // System.out.println("groupId:" + groupId);
            vwMailGroupMappingEntityList = vwMailGroupMappingRepository.findByGroupId(intGroupId);

        } catch (Exception e) {
            log.error("OpvGeneralService.getEmailByGroupId 發生錯誤： " + e.getMessage(), e);
        }
        return vwMailGroupMappingEntityList;
    }

    public List<MailGroupNotificationMappingEntity> getEmail(String groupId){
        List<MailGroupNotificationMappingEntity> mailGroupNotificationMappingEntities =null;
        int intGroupId = Integer.parseInt(groupId);
        try {
            mailGroupNotificationMappingRepository.findAllByGroupId(intGroupId);
        } catch (Exception e) {
            log.error("OpvGeneralService.getEmail 發生錯誤： " + e.getMessage(), e);
        }
        return mailGroupNotificationMappingEntities;
    }



    public void insertMailBlacklist(String mailAddress, String description, String responseJson, String status, String diagnosticCode){
        MailBlacklistEntity mailBlacklistEntity=new MailBlacklistEntity();
        try {
            mailBlacklistEntity.setMailAddress(mailAddress);
            mailBlacklistEntity.setCreateTime(new Timestamp(new Date().getTime()));
            mailBlacklistEntity.setDescription(description);
            mailBlacklistEntity.setResponseJson(responseJson);
            mailBlacklistEntity.setStatus(status);
            mailBlacklistEntity.setDiagnosticCode(diagnosticCode);
            mailBlackListRepository.save(mailBlacklistEntity);
        } catch (Exception e) {
            log.error("OpvGeneralService.insertMailBlacklist 發生錯誤： " + e.getMessage(), e);
        }
    }


}
