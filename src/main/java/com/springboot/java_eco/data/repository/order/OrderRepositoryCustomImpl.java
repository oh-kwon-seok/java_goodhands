package com.springboot.java_eco.data.repository.order;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.OrderController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepositoryCustomImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryCustomImpl(){
        super(Order.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderController.class);


    @Override
    public List<Order> findAll(CommonSearchDto commonSearchDto){
        QOrder order = QOrder.order;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (order.code != null) {
                builder.or(order.code.like("%" + search_text + "%"));
            }
            if (order.name != null) {
                builder.or(order.name.like("%" + search_text + "%"));
            }
            if (order.product_spec != null) {
                builder.or(order.product_spec.like("%" + search_text + "%"));
            }
            if (order.ship_place != null) {
                builder.or(order.ship_place.like("%" + search_text + "%"));
            }

            if (order.description != null) {
                builder.or(order.description.like("%" + search_text + "%"));
            }
            if (order.customer.name != null) {
                builder.or(order.customer.name.like("%" + search_text + "%"));
            }


        }else {
            if("code".equals(filter_title)){
                builder.and(order.code.like("%" + search_text + "%"));
            }
            else if("name".equals(filter_title)){
                builder.and(order.name.like("%" + search_text + "%"));
            }
            else if("product_spec".equals(filter_title)){
                builder.and(order.product_spec.like("%" + search_text + "%"));
            }else if("ship_place".equals(filter_title)){
                builder.and(order.ship_place.like("%" + search_text + "%"));
            }
            else if("description".equals(filter_title)){
                builder.and(order.description.like("%" + search_text + "%"));
            }
            else if("client".equals(filter_title)){
                builder.and(order.customer.name.like("%" + search_text + "%"));
            }

        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = order.used.eq(1);


        Predicate dateRange = order.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<Order> orderList = from(order)
                .select(order)
                .where(predicate,dateRange,used)
                .orderBy(order.created.desc()) // Order by created field in descending order
                .fetch();



        return orderList;

    }

    @Override
    public List<Order> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QOrder order = QOrder.order;

        Predicate used = order.used.eq(1);

        List<Order> orderList = from(order)
                .select(order)
                .where(used)
                .fetch();

        return orderList;

    }
}
