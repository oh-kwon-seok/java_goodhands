package com.springboot.java_eco.data.repository.estimate;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.EstimateController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.Estimate;
import com.springboot.java_eco.data.entity.QEstimate;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EstimateRepositoryCustomImpl extends QuerydslRepositorySupport implements EstimateRepositoryCustom {

    public EstimateRepositoryCustomImpl(){
        super(Estimate.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EstimateController.class);

    
    @Override
    public List<Estimate> findAll(CommonSearchDto commonSearchDto){
        QEstimate estimate = QEstimate.estimate;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (estimate.code != null) {
                builder.or(estimate.code.like("%" + search_text + "%"));
            }
            if (estimate.name != null) {
                builder.or(estimate.name.like("%" + search_text + "%"));
            }
            if (estimate.product_spec != null) {
                builder.or(estimate.product_spec.like("%" + search_text + "%"));
            }
            if (estimate.ship_place != null) {
                builder.or(estimate.ship_place.like("%" + search_text + "%"));
            }

            if (estimate.description != null) {
                builder.or(estimate.description.like("%" + search_text + "%"));
            }
            if (estimate.company.name != null) {
                builder.or(estimate.company.name.like("%" + search_text + "%"));
            }    if (estimate.customer.name != null) {
                builder.or(estimate.customer.name.like("%" + search_text + "%"));
            }


        }else {
            if("code".equals(filter_title)){
                builder.and(estimate.code.like("%" + search_text + "%"));
            }
            else if("name".equals(filter_title)){
                builder.and(estimate.name.like("%" + search_text + "%"));
            }
            else if("product_spec".equals(filter_title)){
                builder.and(estimate.product_spec.like("%" + search_text + "%"));
            }else if("ship_place".equals(filter_title)){
                builder.and(estimate.ship_place.like("%" + search_text + "%"));
            }
            else if("description".equals(filter_title)){
                builder.and(estimate.description.like("%" + search_text + "%"));
            }  else if("company".equals(filter_title)){
                builder.and(estimate.company.name.like("%" + search_text + "%"));
            }else if("client".equals(filter_title)){
                builder.and(estimate.customer.name.like("%" + search_text + "%"));
            }

        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = estimate.used.eq(1);


        Predicate dateRange = estimate.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<Estimate> estimateList = from(estimate)
                .select(estimate)
                .where(predicate,dateRange,used)
                .orderBy(estimate.created.desc()) // Order by created field in descending order
                .fetch();



        return estimateList;

    }

    @Override
    public List<Estimate> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QEstimate estimate = QEstimate.estimate;

        Predicate used = estimate.used.eq(1);

        List<Estimate> estimateList = from(estimate)
                .select(estimate)
                .where(used)
                .fetch();

        return estimateList;

    }
}
