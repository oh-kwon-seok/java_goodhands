package com.springboot.java_eco.data.repository.factorySub;

import com.springboot.java_eco.data.entity.Factory;
import com.springboot.java_eco.data.entity.FactorySub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("factorySubRepositorySupport")
public interface FactorySubRepository extends JpaRepository<FactorySub,String>, FactorySubRepositoryCustom {


    List<FactorySub> findByFactoryUid(Long uid);
    FactorySub findByUid(Long uid);





}
