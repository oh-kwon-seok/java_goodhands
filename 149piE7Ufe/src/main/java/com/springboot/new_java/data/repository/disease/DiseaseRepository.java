package com.springboot.new_java.data.repository.disease;


import com.springboot.new_java.data.entity.disease.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("diseaseRepositorySupport")
public interface DiseaseRepository extends JpaRepository<Disease,Long>, DiseaseRepositoryCustom {

    Disease findByUid(Long uid);

    Disease findByNameAndUsed(String name,  Boolean used);
}
