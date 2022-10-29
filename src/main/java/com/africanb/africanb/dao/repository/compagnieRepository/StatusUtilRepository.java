package com.africanb.africanb.dao.repository.compagnieRepository;

import com.africanb.africanb.dao.entity.compagnie.StatusUtil;
import com.africanb.africanb.helper.dto.compagnie.StatusUtilDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusUtilRepository extends JpaRepository<StatusUtil,Long> {
}
