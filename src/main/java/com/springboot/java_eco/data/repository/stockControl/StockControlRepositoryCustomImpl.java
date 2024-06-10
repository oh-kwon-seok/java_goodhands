package com.springboot.java_eco.data.repository.stockControl;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.StockControlController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.QStockControl;
import com.springboot.java_eco.data.entity.StockControl;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockControlRepositoryCustomImpl extends QuerydslRepositorySupport implements StockControlRepositoryCustom {

    public StockControlRepositoryCustomImpl(){
        super(StockControl.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockControlController.class);


    @Override
    public List<StockControl> findAll(CommonSearchDto commonSearchDto){
        QStockControl stockControl = QStockControl.stockControl;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (stockControl.lot != null) {
                builder.or(stockControl.lot.like("%" + search_text + "%"));
            }
            if (stockControl.item.ingr_eng_name != null) {
                builder.or(stockControl.item.ingr_eng_name.like("%" + search_text + "%"));
            }

        }else {
            if("lot".equals(filter_title)){
                builder.and(stockControl.lot.like("%" + search_text + "%"));
            }
            else if("ingr_eng_name".equals(filter_title)){
                builder.and(stockControl.item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }




        Predicate dateRange = stockControl.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<StockControl> stockControlList = from(stockControl)
                .select(stockControl)
                .where(predicate,dateRange)
                .orderBy(stockControl.created.desc()) // StockControl by created field in descending stockControl
                .fetch();



        return stockControlList;

    }

    @Override
    public List<StockControl> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QStockControl stockControl = QStockControl.stockControl;



        List<StockControl> stockControlList = from(stockControl)
                .select(stockControl)
                .where()
                .fetch();

        return stockControlList;

    }
}
