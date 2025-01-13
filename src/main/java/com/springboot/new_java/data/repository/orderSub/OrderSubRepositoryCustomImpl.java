package com.springboot.new_java.data.repository.orderSub;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.OrderSubController;
import com.springboot.new_java.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.new_java.data.entity.OrderSub;
import com.springboot.new_java.data.entity.QOrder;
import com.springboot.new_java.data.entity.QOrderSub;
import com.springboot.new_java.data.entity.QItem;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderSubRepositoryCustomImpl extends QuerydslRepositorySupport implements OrderSubRepositoryCustom {

    public OrderSubRepositoryCustomImpl(){
        super(OrderSub.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderSubController.class);

    
    @Override
    public List<OrderSub> findAll(OrderSubSearchDto orderSubSearchDto){
        QOrderSub orderSub = QOrderSub.orderSub;
        QItem item = QItem.item;

        String filter_title = orderSubSearchDto.getFilter_title();
        String search_text = orderSubSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){


            if (item.code != null) {
                builder.or(item.code.like("%" + search_text + "%"));
            }

        }else {

            if("code".equals(filter_title)){
                builder.and(item.code.like("%" + search_text + "%"));
            }
        }



        Predicate predicate = builder.getValue();

        List<OrderSub> orderSubList = from(orderSub)
                .select(orderSub)
                .where(predicate)
                .orderBy(orderSub.created.desc()) // Order by created field in descending order
                .fetch();



        return orderSubList;

    }

    @Override
    public List<OrderSub> findInfo(OrderSubSearchDto orderSubSearchDto){

        QOrderSub orderSub = QOrderSub.orderSub;



        List<OrderSub> orderSubList = from(orderSub)
                .select(orderSub)
                .where()
                .fetch();

        return orderSubList;

    }

    @Override
    public List<OrderSub> findByOrderUidSelect(OrderSubSearchDto orderSubSearchDto){
        QItem item = QItem.item;
        QOrder order = QOrder.order;
        QOrderSub orderSub = QOrderSub.orderSub;
        Long search_id = orderSubSearchDto.getOrder_uid();

        Predicate order_uid = order.uid.eq(search_id);
        List<Tuple> results = from(orderSub)
                .leftJoin(orderSub.order, order).fetchJoin()
                .leftJoin(orderSub.item, item).fetchJoin()
                .select(orderSub,order,item)
                .where(order_uid)
                .fetch();
        List<OrderSub> orderSubList = new ArrayList<>();
        for (Tuple result : results) {
            OrderSub orderSubEntity = result.get(orderSub);
            orderSubList.add(orderSubEntity);
        }
        return orderSubList;
    }

}
