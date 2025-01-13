package com.springboot.new_java.data.repository.stockApproval;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.StockApprovalController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.new_java.data.entity.QItem;
import com.springboot.new_java.data.entity.QStockApproval;
import com.springboot.new_java.data.entity.QWorkTask;
import com.springboot.new_java.data.entity.StockApproval;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockApprovalRepositoryCustomImpl extends QuerydslRepositorySupport implements StockApprovalRepositoryCustom {

    public StockApprovalRepositoryCustomImpl(){
        super(StockApproval.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockApprovalController.class);

    @Override
    public List<StockApproval> findAll(CommonSearchDto commonSearchDto){
        QStockApproval stockApproval = QStockApproval.stockApproval;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (stockApproval.item.ingr_eng_name != null) {
                builder.or(stockApproval.item.ingr_eng_name.like("%" + search_text + "%"));
            }



        }else {

            if("ingr_eng_name".equals(filter_title)){
                builder.and(stockApproval.item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가


        Predicate dateRange = stockApproval.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<StockApproval> stockApprovalList = from(stockApproval)
                .select(stockApproval)
                .where(predicate,dateRange)
                .orderBy(stockApproval.created.desc()) // StockApproval by created field in descending stockApproval
                .fetch();



        return stockApprovalList;

    }

    @Override
    public List<StockApproval> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QStockApproval stockApproval = QStockApproval.stockApproval;



        List<StockApproval> stockApprovalList = from(stockApproval)
                .select(stockApproval)
                .where()
                .fetch();

        return stockApprovalList;

    }

    @Override
    public List<StockApproval> findByWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto){
        QItem item = QItem.item;
        QWorkTask workTask = QWorkTask.workTask;
        QStockApproval stockApproval = QStockApproval.stockApproval;


        Long search_id = stockApprovalSearchDto.getWork_task_uid();

        Predicate work_task_uid = workTask.uid.eq(search_id);
        List<Tuple> results = from(stockApproval)
                .leftJoin(stockApproval.workTask, workTask).fetchJoin()
                .leftJoin(stockApproval.item, item).fetchJoin()
                .select(stockApproval,workTask,item)
                .where(work_task_uid)
                .fetch();
        List<StockApproval> stockApprovalList = new ArrayList<>();
        for (Tuple result : results) {
            StockApproval stockApprovalEntity = result.get(stockApproval);
            stockApprovalList.add(stockApprovalEntity);
        }
        return stockApprovalList;
    }
}
