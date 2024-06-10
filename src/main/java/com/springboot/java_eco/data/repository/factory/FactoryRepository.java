package com.springboot.java_eco.data.repository.factory;


import com.springboot.java_eco.data.entity.Factory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("factoryRepositorySupport")
public interface FactoryRepository extends JpaRepository<Factory,String>, FactoryRepositoryCustom {

    Factory getById(String user_id);
    Factory findByUid(Long uid);

}
