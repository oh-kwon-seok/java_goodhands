package com.springboot.new_java.data.repository.shipOrderSub;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.ShipOrderSubController;
import com.springboot.new_java.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.new_java.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShipOrderSubRepositoryCustomImpl extends QuerydslRepositorySupport implements ShipOrderSubRepositoryCustom {

    public ShipOrderSubRepositoryCustomImpl(){
        super(OrderSub.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ShipOrderSubController.class);

    
    @Override
    public List<ShipOrderSub> findAll(ShipOrderSubSearchDto shipOrderSubSearchDto){
        QShipOrderSub shipOrderSub = QShipOrderSub.shipOrderSub;
        QItem item = QItem.item;

        String filter_title = shipOrderSubSearchDto.getFilter_title();
        String search_text = shipOrderSubSearchDto.getSearch_text();


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

        List<ShipOrderSub> shipOrderSubList = from(shipOrderSub)
                .select(shipOrderSub)
                .where(predicate)
                .orderBy(shipOrderSub.created.desc()) // Order by created field in descending order
                .fetch();



        return shipOrderSubList;

    }

    @Override
    public List<ShipOrderSub> findInfo(ShipOrderSubSearchDto shipOrderSubSearchDto){

        QShipOrderSub shipOrderSub = QShipOrderSub.shipOrderSub;



        List<ShipOrderSub> shipOrderSubList = from(shipOrderSub)
                .select(shipOrderSub)
                .where()
                .fetch();

        return shipOrderSubList;

    }

    @Override
    public List<ShipOrderSub> findByShipOrderUidSelect(ShipOrderSubSearchDto shipOrderSubSearchDto){
        QItem item = QItem.item;
        QShipOrder shipOrder = QShipOrder.shipOrder;
        QShipOrderSub shipOrderSub = QShipOrderSub.shipOrderSub;
        Long search_id = shipOrderSubSearchDto.getShip_order_uid();

        Predicate ship_order_uid = shipOrder.uid.eq(search_id);
        List<Tuple> results = from(shipOrderSub)
                .leftJoin(shipOrderSub.shipOrder, shipOrder).fetchJoin()
                .leftJoin(shipOrderSub.item, item).fetchJoin()
                .select(shipOrderSub,shipOrder,item)
                .where(ship_order_uid)
                .fetch();
        List<ShipOrderSub> shipOrderSubList = new ArrayList<>();
        for (Tuple result : results) {
            ShipOrderSub shipOrderSubEntity = result.get(shipOrderSub);
            shipOrderSubList.add(shipOrderSubEntity);
        }
        return shipOrderSubList;
    }

}
