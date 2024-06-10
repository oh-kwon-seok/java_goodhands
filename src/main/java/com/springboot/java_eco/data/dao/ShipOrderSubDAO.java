package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.java_eco.data.entity.ShipOrderSub;

import java.util.List;


public interface ShipOrderSubDAO {


    List<ShipOrderSub> selectShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);
    List<ShipOrderSub> selectTotalShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);

    List<ShipOrderSub> selectShipOrderUidSelect(ShipOrderSubSearchDto shipOrderSubSearchDto);

}
