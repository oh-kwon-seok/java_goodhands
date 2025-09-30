package com.springboot.new_java.data.repository.seniorDisease;


import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.data.entity.senior.SeniorDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SeniorDiseaseRepositorySupport")
public interface SeniorDiseaseRepository extends JpaRepository<SeniorDisease,Long>, SeniorDiseaseRepositoryCustom {

    List<SeniorDisease> findBySeniorUid(Long seniorUid);
//
//
//    SeniorDisease findByNameAndUsed(String name, Boolean used);

}
