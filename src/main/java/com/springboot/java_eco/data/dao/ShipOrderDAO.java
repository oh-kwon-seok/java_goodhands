package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrder.ShipOrderDto;
import com.springboot.java_eco.data.entity.ShipOrder;

import java.util.List;


public interface ShipOrderDAO {


    List<ShipOrder> selectShipOrder(CommonInfoSearchDto commonInfoSearchDto);
    List<ShipOrder> selectTotalShipOrder(CommonSearchDto commonSearchDto);

     CommonResultDto insertShipOrder(ShipOrderDto shipOrderDto)  throws Exception; // 출하처리
    CommonResultDto updateShipOrder(ShipOrderDto shipOrderDto)  throws Exception; // 출하수정(핵심내용만 수정 가능)

    String deleteShipOrder(List<Long> uid) throws Exception;

}
