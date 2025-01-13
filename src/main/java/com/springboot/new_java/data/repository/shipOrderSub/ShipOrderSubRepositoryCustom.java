package com.springboot.new_java.data.repository.shipOrderSub;

import com.springboot.new_java.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.new_java.data.entity.ShipOrderSub;

import java.util.List;

public interface ShipOrderSubRepositoryCustom {


    List<ShipOrderSub> findAll(ShipOrderSubSearchDto orderSubSearchDto);
    List<ShipOrderSub> findInfo(ShipOrderSubSearchDto orderSubSearchDto);


    List<ShipOrderSub> findByShipOrderUidSelect(ShipOrderSubSearchDto orderSubSearchDto);


}
