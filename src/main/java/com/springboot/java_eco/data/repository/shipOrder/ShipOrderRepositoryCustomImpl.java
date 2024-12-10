package com.springboot.java_eco.data.repository.shipOrder;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.ShipOrderController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.ShipOrder;
import com.springboot.java_eco.data.entity.QShipOrder;
import com.springboot.java_eco.data.repository.shipOrder.ShipOrderRepositoryCustom;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ShipOrderRepositoryCustomImpl extends QuerydslRepositorySupport implements ShipOrderRepositoryCustom {

    public ShipOrderRepositoryCustomImpl(){
        super(ShipOrder.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ShipOrderController.class);


    @Override
    public List<ShipOrder> findAll(CommonSearchDto commonSearchDto){
        QShipOrder shipOrder = QShipOrder.shipOrder;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();
        LOGGER.info("custom : {}",end_date);
        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (shipOrder.code != null) {
                builder.or(shipOrder.code.like("%" + search_text + "%"));
            }
            if (shipOrder.name != null) {
                builder.or(shipOrder.name.like("%" + search_text + "%"));
            }
            if (shipOrder.product_spec != null) {
                builder.or(shipOrder.product_spec.like("%" + search_text + "%"));
            }
            if (shipOrder.ship_place != null) {
                builder.or(shipOrder.ship_place.like("%" + search_text + "%"));
            }

            if (shipOrder.description != null) {
                builder.or(shipOrder.description.like("%" + search_text + "%"));
            }
            if (shipOrder.customer.name != null) {
                builder.or(shipOrder.customer.name.like("%" + search_text + "%"));
            }


        }else {
            if("code".equals(filter_title)){
                builder.and(shipOrder.code.like("%" + search_text + "%"));
            }
            else if("name".equals(filter_title)){
                builder.and(shipOrder.name.like("%" + search_text + "%"));
            }
            else if("product_spec".equals(filter_title)){
                builder.and(shipOrder.product_spec.like("%" + search_text + "%"));
            }else if("ship_place".equals(filter_title)){
                builder.and(shipOrder.ship_place.like("%" + search_text + "%"));
            }
            else if("description".equals(filter_title)){
                builder.and(shipOrder.description.like("%" + search_text + "%"));
            }
            else if("client".equals(filter_title)){
                builder.and(shipOrder.customer.name.like("%" + search_text + "%"));
            }

        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = shipOrder.used.eq(1);


        Predicate dateRange = shipOrder.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<ShipOrder> shipList = from(shipOrder)
                .select(shipOrder)
                .where(predicate,dateRange,used)
                .orderBy(shipOrder.created.desc()) // ShipOrder by created field in descending ship
                .fetch();



        return shipList;

    }

    @Override
    public List<ShipOrder> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QShipOrder shipOrder = QShipOrder.shipOrder;

        Predicate used = shipOrder.used.eq(1);

        List<ShipOrder> shipList = from(shipOrder)
                .select(shipOrder)
                .where(used)
                .fetch();

        return shipList;

    }
}
