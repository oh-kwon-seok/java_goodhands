package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.shipOrder.ShipOrderDto;
import com.springboot.new_java.data.entity.ShipOrder;
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
