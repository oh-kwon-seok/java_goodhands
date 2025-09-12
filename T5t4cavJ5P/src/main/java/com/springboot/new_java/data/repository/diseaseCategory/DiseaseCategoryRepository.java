package com.springboot.new_java.data.repository.diseaseCategory;



import com.springboot.new_java.data.entity.disease.DiseaseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DiseaseCategoryRepositorySupport")
public interface DiseaseCategoryRepository extends JpaRepository<DiseaseCategory,Long>, DiseaseCategoryRepositoryCustom {

    DiseaseCategory findByUid(Long uid);


    DiseaseCategory findByNameAndUsed(String name, Boolean used);

}
