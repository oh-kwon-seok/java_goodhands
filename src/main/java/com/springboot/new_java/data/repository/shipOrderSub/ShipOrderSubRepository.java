package com.springboot.new_java.data.repository.shipOrderSub;


import com.springboot.new_java.data.entity.OrderSub;
import com.springboot.new_java.data.entity.ShipOrderSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shipOrderSubRepositorySupport")
public interface ShipOrderSubRepository extends JpaRepository<ShipOrderSub,String>, ShipOrderSubRepositoryCustom {

    ShipOrderSub getById(String user_id);
    OrderSub findByUid(Long uid);

    List<ShipOrderSub> findByShipOrderUid(Long uid);


}
