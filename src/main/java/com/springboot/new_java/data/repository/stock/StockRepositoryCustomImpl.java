package com.springboot.new_java.data.repository.stock;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.StockController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stock.LotSearchDto;
import com.springboot.new_java.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockRepositoryCustomImpl extends QuerydslRepositorySupport implements StockRepositoryCustom {

    public StockRepositoryCustomImpl(){
        super(Stock.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockController.class);


    @Override
    public List<Stock> findAll(CommonSearchDto commonSearchDto){
        QStock stock = QStock.stock;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (stock.lot != null) {
                builder.or(stock.lot.like("%" + search_text + "%"));
            }
            if (stock.item.ingr_eng_name != null) {
                builder.or(stock.item.ingr_eng_name.like("%" + search_text + "%"));
            }



        }else {
            if("lot".equals(filter_title)){
                builder.and(stock.lot.like("%" + search_text + "%"));
            }
            else if("ingr_eng_name".equals(filter_title)){
                builder.and(stock.item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가


        Predicate dateRange = stock.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<Stock> stockList = from(stock)
                .select(stock)
                .where(predicate,dateRange)
                .orderBy(stock.created.desc()) // Stock by created field in descending stock
                .fetch();



        return stockList;

    }

    @Override
    public List<Stock> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QStock stock = QStock.stock;



        List<Stock> stockList = from(stock)
                .select(stock)
                .where()
                .fetch();

        return stockList;

    }

    @Override
    public List<Stock> findLotStock(LotSearchDto lotSearchDto){
        QStock stock = QStock.stock;

        String lot = lotSearchDto.getLot();
        Long company = lotSearchDto.getCompany_uid();
        Long factory = lotSearchDto.getFactory_uid();
        Long factorySub = lotSearchDto.getFactory_sub_uid();



        List<Stock> stockList = from(stock)
                .select(stock)
                .where(
                        stock.lot.eq(lot),
                        stock.company.uid.eq(company),
                        stock.factory.uid.eq(factory),
                        stock.factorySub.uid.eq(factorySub)
                )
                .orderBy(stock.created.desc()) // Stock by created field in descending stock
                .fetch();



        return stockList;

    }
    @Override
    public List<Stock> findPackingLotStock(LotSearchDto lotSearchDto){
        QStock stock = QStock.stock;

        String lot = lotSearchDto.getLot();
        Long company = lotSearchDto.getCompany_uid();

        List<Stock> stockList = from(stock)
                .select(stock)
                .where(
                        stock.lot.eq(lot),
                        stock.company.uid.eq(company),
                        stock.status.eq("가용")

                )
                .orderBy(stock.created.desc()) // Stock by created field in descending stock
                .fetch();



        return stockList;

    }
}
