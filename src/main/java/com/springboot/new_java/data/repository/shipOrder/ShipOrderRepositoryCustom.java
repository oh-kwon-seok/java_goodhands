package com.springboot.new_java.data.repository.shipOrder;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;

import com.springboot.new_java.data.entity.ShipOrder;

import java.util.List;

public interface ShipOrderRepositoryCustom {


    List<ShipOrder> findAll(CommonSearchDto commonSearchDto);
    List<ShipOrder> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
