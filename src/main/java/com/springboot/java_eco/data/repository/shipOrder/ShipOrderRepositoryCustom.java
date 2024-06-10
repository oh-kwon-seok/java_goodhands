package com.springboot.java_eco.data.repository.shipOrder;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;

import com.springboot.java_eco.data.entity.ShipOrder;

import java.util.List;

public interface ShipOrderRepositoryCustom {


    List<ShipOrder> findAll(CommonSearchDto commonSearchDto);
    List<ShipOrder> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
