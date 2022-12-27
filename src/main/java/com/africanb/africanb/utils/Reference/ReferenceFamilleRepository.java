package com.africanb.africanb.utils.Reference;

import com.africanb.africanb.dao.entity.compagnie.FamilleStatusUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceFamilleRepository extends JpaRepository<ReferenceFamille,Long> {

    @Query("select rf from  ReferenceFamille rf where rf.id = :id and rf.isDeleted= :isDeleted")
    ReferenceFamille findOne(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);

    @Query("select rf from FamilleStatusUtil rf where rf.designation = :designation and rf.isDeleted= :isDeleted")
    ReferenceFamille findByDesignation(@Param("designation") String code, @Param("isDeleted") Boolean isDeleted);

}
