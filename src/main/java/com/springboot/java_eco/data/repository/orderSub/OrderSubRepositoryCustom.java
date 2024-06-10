package com.springboot.java_eco.data.repository.orderSub;

import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;

import java.util.List;

public interface OrderSubRepositoryCustom {


    List<OrderSub> findAll(OrderSubSearchDto orderSubSearchDto);
    List<OrderSub> findInfo(OrderSubSearchDto orderSubSearchDto);


    List<OrderSub> findByOrderUidSelect(OrderSubSearchDto orderSubSearchDto);


}
