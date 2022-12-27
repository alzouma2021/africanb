package com.africanb.africanb.utils.Reference;

import com.africanb.africanb.dao.entity.compagnie.StatusUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference,Long> {

    @Query("select r from  Reference r where r.id = :id and r.isDeleted= :isDeleted")
    Reference findOne(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);

    @Query("select r from Reference r where r.designation = :designation and r.isDeleted= :isDeleted")
    Reference findByDesignation(@Param("designation") String code, @Param("isDeleted") Boolean isDeleted);

}
