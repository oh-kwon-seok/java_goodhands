package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.order.OrderDto;
import com.springboot.new_java.data.entity.Order;

import java.util.List;


public interface OrderDAO {


    List<Order> selectOrder(CommonInfoSearchDto commonInfoSearchDto);
    List<Order> selectTotalOrder(CommonSearchDto commonSearchDto);


     CommonResultDto insertOrder(OrderDto orderDto)  throws Exception;

    CommonResultDto updateOrder(OrderDto orderDto) throws Exception;


    String deleteOrder(List<Long> uid) throws Exception;



}
