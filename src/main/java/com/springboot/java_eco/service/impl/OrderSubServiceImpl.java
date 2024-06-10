package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.OrderSubDAO;
import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;
import com.springboot.java_eco.service.OrderSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSubServiceImpl implements OrderSubService {
    private final OrderSubDAO orderSubDAO;

    @Autowired
    public OrderSubServiceImpl(@Qualifier("orderSubDAOImpl") OrderSubDAO orderSubDAO){
        this.orderSubDAO = orderSubDAO;
    }

    @Override
    public List<OrderSub> getTotalOrderSub(OrderSubSearchDto orderSubSearchDto){
        return orderSubDAO.selectTotalOrderSub(orderSubSearchDto);
    }

  

    @Override
    public List<OrderSub> getOrderSub(OrderSubSearchDto orderSubSearchDto){
        return orderSubDAO.selectOrderSub(orderSubSearchDto);
    }


    @Override
    public List<OrderSub> getOrderUidSelect(OrderSubSearchDto orderSubSearchDto){
        return orderSubDAO.selectOrderUidSelect(orderSubSearchDto);
    }




}
