package com.springboot.new_java.data.repository.order;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {


    List<Order> findAll(CommonSearchDto commonSearchDto);
    List<Order> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
