package com.springboot.java_eco.data.repository.shipOrder;


import com.springboot.java_eco.data.entity.Company;

import com.springboot.java_eco.data.entity.ShipOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("shipOrderRepositorySupport")
public interface ShipOrderRepository extends JpaRepository<ShipOrder,String>, ShipOrderRepositoryCustom {

    ShipOrder getById(String user_id);
    ShipOrder findByUid(Long uid);
    ShipOrder findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
