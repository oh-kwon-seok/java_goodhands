package com.springboot.java_eco.data.dao;


import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;

import java.util.List;


public interface OrderSubDAO {


    List<OrderSub> selectTotalOrderSub(OrderSubSearchDto orderSubSearchDto);


    List<OrderSub> selectOrderSub(OrderSubSearchDto orderSubSearchDto);

    List<OrderSub> selectOrderUidSelect(OrderSubSearchDto orderSubSearchDto);



}
