package com.springboot.new_java.data.repository.order;


import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderRepositorySupport")
public interface OrderRepository extends JpaRepository<Order,String>, OrderRepositoryCustom {

    Order getById(String user_id);
    Order findByUid(Long uid);
    Order findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
