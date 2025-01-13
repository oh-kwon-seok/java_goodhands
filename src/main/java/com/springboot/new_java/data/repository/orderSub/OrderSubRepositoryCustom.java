package com.springboot.new_java.data.repository.orderSub;

import com.springboot.new_java.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.new_java.data.entity.OrderSub;

import java.util.List;

public interface OrderSubRepositoryCustom {


    List<OrderSub> findAll(OrderSubSearchDto orderSubSearchDto);
    List<OrderSub> findInfo(OrderSubSearchDto orderSubSearchDto);


    List<OrderSub> findByOrderUidSelect(OrderSubSearchDto orderSubSearchDto);


}
