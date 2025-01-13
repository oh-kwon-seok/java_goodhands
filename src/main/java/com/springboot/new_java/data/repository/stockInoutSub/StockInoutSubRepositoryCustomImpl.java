package com.springboot.new_java.data.repository.stockInoutSub;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.StockInoutSubController;
import com.springboot.new_java.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.new_java.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockInoutSubRepositoryCustomImpl extends QuerydslRepositorySupport implements StockInoutSubRepositoryCustom {

    public StockInoutSubRepositoryCustomImpl(){
        super(StockInoutSub.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockInoutSubController.class);

    
    @Override
    public List<StockInoutSub> findAll(StockInoutSubSearchDto stockInoutSubSearchDto){
        QStockInoutSub stockInoutSub = QStockInoutSub.stockInoutSub;
        QItem item = QItem.item;

        String filter_title = stockInoutSubSearchDto.getFilter_title();
        String search_text = stockInoutSubSearchDto.getSearch_text();


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

        List<StockInoutSub> stockInoutSubList = from(stockInoutSub)
                .select(stockInoutSub)
                .where(predicate)
                .orderBy(stockInoutSub.created.desc()) // Order by created field in descending order
                .fetch();



        return stockInoutSubList;

    }

    @Override
    public List<StockInoutSub> findInfo(StockInoutSubSearchDto stockInoutSubSearchDto){

        QStockInoutSub stockInoutSub = QStockInoutSub.stockInoutSub;



        List<StockInoutSub> stockInoutSubList = from(stockInoutSub)
                .select(stockInoutSub)
                .where()
                .fetch();

        return stockInoutSubList;

    }

    @Override
    public List<StockInoutSub> findByStockInoutUidSelect(StockInoutSubSearchDto stockInoutSubSearchDto){
        QItem item = QItem.item;
        QStockInout stockInout = QStockInout.stockInout;
        QStockInoutSub stockInoutSub = QStockInoutSub.stockInoutSub;
        Long search_id = stockInoutSubSearchDto.getStock_inout_uid();

        Predicate stock_inout_uid = stockInout.uid.eq(search_id);
        List<Tuple> results = from(stockInoutSub)
                .leftJoin(stockInoutSub.stockInout,stockInout).fetchJoin()
                .leftJoin(stockInoutSub.item, item).fetchJoin()
                .select(stockInoutSub,stockInout,item)
                .where(stock_inout_uid)
                .fetch();
        List<StockInoutSub> stockInoutSubList = new ArrayList<>();
        for (Tuple result : results) {
            StockInoutSub stockInoutSubEntity = result.get(stockInoutSub);
            stockInoutSubList.add(stockInoutSubEntity);
        }
        return stockInoutSubList;
    }

}
