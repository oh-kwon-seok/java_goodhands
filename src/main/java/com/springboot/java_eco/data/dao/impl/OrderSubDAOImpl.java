package com.springboot.java_eco.data.dao.impl;

import com.springboot.java_eco.data.dao.OrderSubDAO;
import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.order.OrderRepository;
import com.springboot.java_eco.data.repository.orderSub.OrderSubRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderSubDAOImpl implements OrderSubDAO {

    private final OrderSubRepository orderSubRepository;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;


    @Autowired
    public OrderSubDAOImpl(OrderSubRepository orderSubRepository, OrderRepository orderRepository, CompanyRepository companyRepository, ItemRepository itemRepository){
        this.orderSubRepository = orderSubRepository;
        this.orderRepository = orderRepository;
        this.companyRepository = companyRepository;
        this.itemRepository = itemRepository;

    }

    @Override
    public List<OrderSub> selectTotalOrderSub(OrderSubSearchDto orderSubSearchDto) {
        return orderSubRepository.findAll(orderSubSearchDto);

    }


    @Override
    public List<OrderSub> selectOrderSub(OrderSubSearchDto orderSubSearchDto) {
        return orderSubRepository.findInfo(orderSubSearchDto);

    }
    @Override
    public List<OrderSub> selectOrderUidSelect(OrderSubSearchDto orderSubSearchDto) {
        return orderSubRepository.findByOrderUidSelect(orderSubSearchDto);

    }



}
