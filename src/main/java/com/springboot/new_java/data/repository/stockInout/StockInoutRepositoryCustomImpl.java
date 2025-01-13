package com.springboot.new_java.data.repository.stockInout;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.StockInoutController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.StockInout;
import com.springboot.new_java.data.entity.QStockInout;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockInoutRepositoryCustomImpl extends QuerydslRepositorySupport implements StockInoutRepositoryCustom {

    public StockInoutRepositoryCustomImpl(){
        super(StockInout.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockInoutController.class);


    @Override
    public List<StockInout> findAll(CommonSearchDto commonSearchDto){
        QStockInout stockInout = QStockInout.stockInout;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){


            if (stockInout.doc_type != null) {
                builder.or(stockInout.doc_type.like("%" + search_text + "%"));
            }
            if (stockInout.status != null) {
                builder.or(stockInout.status.like("%" + search_text + "%"));
            }



        }else {

            if("doc_type".equals(filter_title)){
                builder.and(stockInout.doc_type.like("%" + search_text + "%"));
            }
            else if("status".equals(filter_title)){
                builder.and(stockInout.status.like("%" + search_text + "%"));
            }

        }


        Predicate dateRange = stockInout.created.between(start_date, end_date);


        Predicate predicate = builder.getValue();

        List<StockInout> stockInoutList = from(stockInout)
                .select(stockInout)
                .where(predicate,dateRange)
                .orderBy(stockInout.created.desc()) // StockInout by created field in descending stockInout
                .fetch();

        return stockInoutList;

    }

    @Override
    public List<StockInout> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QStockInout stockInout = QStockInout.stockInout;



        List<StockInout> stockInoutList = from(stockInout)
                .select(stockInout)
                .where()
                .fetch();

        return stockInoutList;

    }
}
