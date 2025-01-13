package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.order.OrderDto;
import com.springboot.new_java.data.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<Order> getOrder(CommonInfoSearchDto commonInfoSearchDto);

    List<Order> getTotalOrder(CommonSearchDto commonSearchDto);


    CommonResultDto saveOrder(OrderDto orderDto) throws Exception;

    CommonResultDto updateOrder(OrderDto orderDto) throws Exception;

    void deleteOrder(List<Long> uid) throws Exception;


}
