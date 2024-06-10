package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrder.ShipOrderDto;
import com.springboot.java_eco.data.entity.ShipOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipOrderService {

    List<ShipOrder> getShipOrder(CommonInfoSearchDto commonInfoSearchDto);

    List<ShipOrder> getTotalShipOrder(CommonSearchDto commonSearchDto);


    CommonResultDto saveShipOrder(ShipOrderDto shipOrderDto) throws Exception;
    CommonResultDto updateShipOrder(ShipOrderDto shipOrderDto) throws Exception;


    void deleteShipOrder(List<Long> uid) throws Exception;


}
