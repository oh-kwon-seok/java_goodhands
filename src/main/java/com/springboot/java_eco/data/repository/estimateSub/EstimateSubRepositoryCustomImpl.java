package com.springboot.java_eco.data.repository.estimateSub;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.EstimateSubController;
import com.springboot.java_eco.data.dto.estimateSub.EstimateSubSearchDto;
import com.springboot.java_eco.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EstimateSubRepositoryCustomImpl extends QuerydslRepositorySupport implements EstimateSubRepositoryCustom {

    public EstimateSubRepositoryCustomImpl(){
        super(EstimateSub.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EstimateSubController.class);

    
    @Override
    public List<EstimateSub> findAll(EstimateSubSearchDto estimateSubSearchDto){
        QEstimateSub estimateSub = QEstimateSub.estimateSub;
        QItem item = QItem.item;

        String filter_title = estimateSubSearchDto.getFilter_title();
        String search_text = estimateSubSearchDto.getSearch_text();


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

        List<EstimateSub> estimateSubList = from(estimateSub)
                .select(estimateSub)
                .where(predicate)
                .orderBy(estimateSub.created.desc()) // Order by created field in descending order
                .fetch();



        return estimateSubList;

    }

    @Override
    public List<EstimateSub> findInfo(EstimateSubSearchDto estimateSubSearchDto){

        QEstimateSub estimateSub = QEstimateSub.estimateSub;



        List<EstimateSub> estimateSubList = from(estimateSub)
                .select(estimateSub)
                .where()
                .fetch();

        return estimateSubList;

    }

    @Override
    public List<EstimateSub> findByEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto){
        QItem item = QItem.item;
        QEstimate estimate = QEstimate.estimate;
        QEstimateSub estimateSub = QEstimateSub.estimateSub;
        Long search_id = estimateSubSearchDto.getEstimate_uid();

        Predicate estimate_uid = estimate.uid.eq(search_id);
        List<Tuple> results = from(estimateSub)
                .leftJoin(estimateSub.estimate, estimate).fetchJoin()
                .leftJoin(estimateSub.item, item).fetchJoin()
                .select(estimateSub,estimate,item)
                .where(estimate_uid)
                .fetch();
        List<EstimateSub> estimateSubList = new ArrayList<>();
        for (Tuple result : results) {
            EstimateSub estimateSubEntity = result.get(estimateSub);
            estimateSubList.add(estimateSubEntity);
        }
        return estimateSubList;
    }

}
