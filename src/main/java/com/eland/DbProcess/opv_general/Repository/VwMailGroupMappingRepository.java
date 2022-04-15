package com.eland.DbProcess.opv_general.Repository;


import com.eland.DbProcess.opv_general.Entity.VwMailGroupMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwMailGroupMappingRepository extends JpaRepository<VwMailGroupMappingEntity, Long> {
    List<VwMailGroupMappingEntity> findByGroupId(int groupId);

}
