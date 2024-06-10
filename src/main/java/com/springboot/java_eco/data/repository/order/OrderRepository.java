package com.springboot.java_eco.data.repository.order;


import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderRepositorySupport")
public interface OrderRepository extends JpaRepository<Order,String>, OrderRepositoryCustom {

    Order getById(String user_id);
    Order findByUid(Long uid);
    Order findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
