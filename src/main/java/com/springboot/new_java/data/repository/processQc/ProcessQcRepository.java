package com.springboot.new_java.data.repository.processQc;

import com.springboot.new_java.data.entity.ProcessQc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("processQcRepositorySupport")
public interface ProcessQcRepository extends JpaRepository<ProcessQc,String>, ProcessQcRepositoryCustom {


    List<ProcessQc> findByProcessUid(Long uid);





}
