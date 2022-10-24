package com.africanb.africanb.dao.repository.compagnieRepository;

import com.africanb.africanb.dao.entity.compagnie.StatusUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusUtilRepository extends JpaRepository<StatusUtil,Long> {
}
