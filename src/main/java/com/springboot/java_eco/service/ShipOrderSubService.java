package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.java_eco.data.entity.ShipOrderSub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipOrderSubService {

    List<ShipOrderSub> getShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);

    List<ShipOrderSub> getTotalShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);


    List<ShipOrderSub> getShipOrderUidSelect(ShipOrderSubSearchDto shipOrderSubSearchDto);


}
