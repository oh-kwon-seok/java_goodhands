package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderSubService {

    List<OrderSub> getTotalOrderSub(OrderSubSearchDto orderSubSearchDto);

    List<OrderSub> getOrderSub(OrderSubSearchDto orderSubSearchDto);

    List<OrderSub> getOrderUidSelect(OrderSubSearchDto orderSubSearchDto);




}
