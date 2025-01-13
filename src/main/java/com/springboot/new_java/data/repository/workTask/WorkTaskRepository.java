package com.springboot.new_java.data.repository.workTask;


import com.springboot.new_java.data.entity.WorkTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("workTaskRepositorySupport")
public interface WorkTaskRepository extends JpaRepository<WorkTask,Long>, WorkTaskRepositoryCustom {

    WorkTask findByUid(Long uid);



}
