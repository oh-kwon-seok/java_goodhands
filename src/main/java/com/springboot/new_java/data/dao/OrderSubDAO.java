package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.new_java.data.entity.OrderSub;

import java.util.List;


public interface OrderSubDAO {


    List<OrderSub> selectTotalOrderSub(OrderSubSearchDto orderSubSearchDto);


    List<OrderSub> selectOrderSub(OrderSubSearchDto orderSubSearchDto);

    List<OrderSub> selectOrderUidSelect(OrderSubSearchDto orderSubSearchDto);



}
