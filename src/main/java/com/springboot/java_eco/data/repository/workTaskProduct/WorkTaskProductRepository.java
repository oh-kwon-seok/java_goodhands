package com.springboot.java_eco.data.repository.workTaskProduct;


import com.springboot.java_eco.data.entity.WorkTaskProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("workTaskProductRepositorySupport")
public interface WorkTaskProductRepository extends JpaRepository<WorkTaskProduct,String>, WorkTaskProductRepositoryCustom {

    WorkTaskProduct findByUid(Long uid);

}
