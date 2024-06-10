package com.springboot.java_eco.data.repository.shipOrderSub;


import com.springboot.java_eco.data.entity.OrderSub;
import com.springboot.java_eco.data.entity.ShipOrderSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shipOrderSubRepositorySupport")
public interface ShipOrderSubRepository extends JpaRepository<ShipOrderSub,String>, ShipOrderSubRepositoryCustom {

    ShipOrderSub getById(String user_id);
    OrderSub findByUid(Long uid);

    List<ShipOrderSub> findByShipOrderUid(Long uid);


}
