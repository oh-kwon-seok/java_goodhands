package com.springboot.java_eco.data.repository.shipOrderSub;

import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;
import com.springboot.java_eco.data.entity.ShipOrderSub;

import java.util.List;

public interface ShipOrderSubRepositoryCustom {


    List<ShipOrderSub> findAll(ShipOrderSubSearchDto orderSubSearchDto);
    List<ShipOrderSub> findInfo(ShipOrderSubSearchDto orderSubSearchDto);


    List<ShipOrderSub> findByShipOrderUidSelect(ShipOrderSubSearchDto orderSubSearchDto);


}
