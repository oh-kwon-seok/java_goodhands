package com.springboot.new_java.data.repository.factory;


import com.springboot.new_java.data.entity.Factory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("factoryRepositorySupport")
public interface FactoryRepository extends JpaRepository<Factory,String>, FactoryRepositoryCustom {

    Factory getById(String user_id);
    Factory findByUid(Long uid);

}
