package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.OrderDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.order.OrderDto;
import com.springboot.new_java.data.entity.Order;
import com.springboot.new_java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(@Qualifier("orderDAOImpl") OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    }

    @Override
    public List<Order> getOrder(CommonInfoSearchDto commonInfoSearchDto){
        return orderDAO.selectOrder(commonInfoSearchDto);
    }
    @Override
    public List<Order> getTotalOrder(CommonSearchDto commonSearchDto){
        return orderDAO.selectTotalOrder(commonSearchDto);
    }
    @Override
    public CommonResultDto saveOrder(OrderDto orderDto) throws Exception {

        return orderDAO.insertOrder(orderDto);

    }
    @Override
    public CommonResultDto updateOrder(OrderDto orderDto) throws Exception {
        return orderDAO.updateOrder(orderDto);
    }
    @Override
    public void deleteOrder(List<Long> uid) throws Exception {
        orderDAO.deleteOrder(uid);
    }


}
