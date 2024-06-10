package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.order.OrderDto;
import com.springboot.java_eco.data.entity.Order;

import java.util.List;


public interface OrderDAO {


    List<Order> selectOrder(CommonInfoSearchDto commonInfoSearchDto);
    List<Order> selectTotalOrder(CommonSearchDto commonSearchDto);


     CommonResultDto insertOrder(OrderDto orderDto)  throws Exception;

    CommonResultDto updateOrder(OrderDto orderDto) throws Exception;


    String deleteOrder(List<Long> uid) throws Exception;



}
