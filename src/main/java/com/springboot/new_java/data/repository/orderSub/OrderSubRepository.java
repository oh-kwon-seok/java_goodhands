package com.springboot.new_java.data.repository.orderSub;


import com.springboot.new_java.data.entity.OrderSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderSubRepositorySupport")
public interface OrderSubRepository extends JpaRepository<OrderSub,String>, OrderSubRepositoryCustom {

    OrderSub getById(String user_id);
    OrderSub findByUid(Long uid);

    List<OrderSub> findByOrderUid(Long uid);


}
