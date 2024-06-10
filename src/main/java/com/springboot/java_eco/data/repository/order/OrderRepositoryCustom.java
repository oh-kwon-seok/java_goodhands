package com.springboot.java_eco.data.repository.order;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {


    List<Order> findAll(CommonSearchDto commonSearchDto);
    List<Order> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
