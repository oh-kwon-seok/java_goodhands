package com.springboot.java_eco.data.repository.workTaskPacking;


import com.springboot.java_eco.data.entity.WorkTaskPacking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("workTaskPackingRepositorySupport")
public interface WorkTaskPackingRepository extends JpaRepository<WorkTaskPacking,String>, WorkTaskPackingRepositoryCustom {

    WorkTaskPacking findByUid(Long uid);

}
