package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.new_java.data.entity.ShipOrderSub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipOrderSubService {

    List<ShipOrderSub> getShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);

    List<ShipOrderSub> getTotalShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto);


    List<ShipOrderSub> getShipOrderUidSelect(ShipOrderSubSearchDto shipOrderSubSearchDto);


}
