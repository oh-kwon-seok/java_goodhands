package com.springboot.new_java.data.repository.stockRecord;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.StockRecordController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.QStockRecord;
import com.springboot.new_java.data.entity.StockRecord;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockRecordRepositoryCustomImpl extends QuerydslRepositorySupport implements StockRecordRepositoryCustom {

    public StockRecordRepositoryCustomImpl(){
        super(StockRecord.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockRecordController.class);


    @Override
    public List<StockRecord> findAll(CommonSearchDto commonSearchDto){
        QStockRecord stockRecord = QStockRecord.stockRecord;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (stockRecord.lot != null) {
                builder.or(stockRecord.lot.like("%" + search_text + "%"));
            }
            if (stockRecord.item.ingr_eng_name != null) {
                builder.or(stockRecord.item.ingr_eng_name.like("%" + search_text + "%"));
            }

        }else {
            if("lot".equals(filter_title)){
                builder.and(stockRecord.lot.like("%" + search_text + "%"));
            }
            else if("ingr_eng_name".equals(filter_title)){
                builder.and(stockRecord.item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가


        Predicate dateRange = stockRecord.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<StockRecord> stockRecordList = from(stockRecord)
                .select(stockRecord)
                .where(predicate,dateRange)
                .orderBy(stockRecord.created.desc()) // StockRecord by created field in descending stockRecord
                .fetch();



        return stockRecordList;

    }

    @Override
    public List<StockRecord> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QStockRecord stockRecord = QStockRecord.stockRecord;



        List<StockRecord> stockRecordList = from(stockRecord)
                .select(stockRecord)
                .where()
                .fetch();

        return stockRecordList;

    }
}
