package com.springboot.java_eco.data.repository.stockRequest;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.StockRequestController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.java_eco.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockRequestRepositoryCustomImpl extends QuerydslRepositorySupport implements StockRequestRepositoryCustom {

    public StockRequestRepositoryCustomImpl(){
        super(StockRequest.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockRequestController.class);

    @Override
    public List<StockRequest> findAll(CommonSearchDto commonSearchDto){
        QStockRequest stockRequest = QStockRequest.stockRequest;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (stockRequest.item.ingr_eng_name != null) {
                builder.or(stockRequest.item.ingr_eng_name.like("%" + search_text + "%"));
            }



        }else {

            if("ingr_eng_name".equals(filter_title)){
                builder.and(stockRequest.item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가


        Predicate dateRange = stockRequest.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<StockRequest> stockRequestList = from(stockRequest)
                .select(stockRequest)
                .where(predicate,dateRange)
                .orderBy(stockRequest.created.desc()) // StockRequest by created field in descending stockRequest
                .fetch();



        return stockRequestList;

    }

    @Override
    public List<StockRequest> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QStockRequest stockRequest = QStockRequest.stockRequest;



        List<StockRequest> stockRequestList = from(stockRequest)
                .select(stockRequest)
                .where()
                .fetch();

        return stockRequestList;

    }

    @Override
    public List<StockRequest> findByWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto){
        QItem item = QItem.item;
        QWorkTask workTask = QWorkTask.workTask;
        QStockRequest stockRequest = QStockRequest.stockRequest;


        Long search_id = stockRequestSearchDto.getWork_task_uid();

        Predicate work_task_uid = workTask.uid.eq(search_id);
        List<Tuple> results = from(stockRequest)
                .leftJoin(stockRequest.workTask, workTask).fetchJoin()
                .leftJoin(stockRequest.item, item).fetchJoin()
                .select(stockRequest,workTask,item)
                .where(work_task_uid)
                .fetch();
        List<StockRequest> stockRequestList = new ArrayList<>();
        for (Tuple result : results) {
            StockRequest stockRequestEntity = result.get(stockRequest);
            stockRequestList.add(stockRequestEntity);
        }
        return stockRequestList;
    }
}
