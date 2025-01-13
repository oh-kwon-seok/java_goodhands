package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.new_java.data.entity.OrderSub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderSubService {

    List<OrderSub> getTotalOrderSub(OrderSubSearchDto orderSubSearchDto);

    List<OrderSub> getOrderSub(OrderSubSearchDto orderSubSearchDto);

    List<OrderSub> getOrderUidSelect(OrderSubSearchDto orderSubSearchDto);




}
