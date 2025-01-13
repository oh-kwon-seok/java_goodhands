package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.new_java.data.entity.ShipOrderSub;

import java.util.List;


public interface ShipOrderSubDAO {


    List<ShipOrderSub> selectShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);
    List<ShipOrderSub> selectTotalShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);

    List<ShipOrderSub> selectShipOrderUidSelect(ShipOrderSubSearchDto shipOrderSubSearchDto);

}
