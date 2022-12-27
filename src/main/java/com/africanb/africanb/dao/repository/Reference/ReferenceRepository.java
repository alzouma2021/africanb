package com.africanb.africanb.dao.repository.Reference;

import com.africanb.africanb.dao.entity.offreVoyage.PrixOffreVoyage;
import com.africanb.africanb.utils.Reference.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference,Long> {
    @Query("select r from  Reference r where r.id = :id and r.isDeleted= :isDeleted")
    PrixOffreVoyage findOne(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);

    @Query("select r from Reference r where r.designation = :designation and r.isDeleted= :isDeleted")
    PrixOffreVoyage findByDesignation(@Param("designation") String designation, @Param("isDeleted") Boolean isDeleted);
}
