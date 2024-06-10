package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.order.OrderDto;
import com.springboot.java_eco.data.entity.Order;
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
