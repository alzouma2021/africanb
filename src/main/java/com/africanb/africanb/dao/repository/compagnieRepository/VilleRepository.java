package com.africanb.africanb.dao.repository.compagnieRepository;

import com.africanb.africanb.dao.entity.compagnie.CompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.Pays;
import com.africanb.africanb.dao.entity.compagnie.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VilleRepository extends JpaRepository<Ville,Long> {

    @Query("select v from  Ville v where v.id = :id and v.isDeleted= :isDeleted")
    Ville findOne(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);

    @Query("select v from Ville v where v.designation = :designation and v.isDeleted= :isDeleted")
    Ville findByDesignation(@Param("designation") String code, @Param("isDeleted") Boolean isDeleted);

}
