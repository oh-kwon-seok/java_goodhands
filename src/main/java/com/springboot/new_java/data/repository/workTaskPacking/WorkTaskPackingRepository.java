package com.springboot.new_java.data.repository.workTaskPacking;


import com.springboot.new_java.data.entity.WorkTaskPacking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("workTaskPackingRepositorySupport")
public interface WorkTaskPackingRepository extends JpaRepository<WorkTaskPacking,String>, WorkTaskPackingRepositoryCustom {

    WorkTaskPacking findByUid(Long uid);

}
