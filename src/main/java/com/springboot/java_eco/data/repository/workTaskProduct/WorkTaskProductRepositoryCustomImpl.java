package com.springboot.java_eco.data.repository.workTaskProduct;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.WorkTaskProductController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.java_eco.data.entity.QBom;
import com.springboot.java_eco.data.entity.QWorkTaskProduct;
import com.springboot.java_eco.data.entity.QWorkTask;
import com.springboot.java_eco.data.entity.WorkTaskProduct;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkTaskProductRepositoryCustomImpl extends QuerydslRepositorySupport implements WorkTaskProductRepositoryCustom {

    public WorkTaskProductRepositoryCustomImpl(){
        super(WorkTaskProduct.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskProductController.class);

    @Override
    public List<WorkTaskProduct> findAll(CommonSearchDto commonSearchDto){
        QWorkTaskProduct workTaskProduct = QWorkTaskProduct.workTaskProduct;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (workTaskProduct.bom.code != null) {
                builder.or(workTaskProduct.bom.code.like("%" + search_text + "%"));
            }



        }else {

            if("code".equals(filter_title)){
                builder.and(workTaskProduct.bom.code.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가


        Predicate dateRange = workTaskProduct.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<WorkTaskProduct> workTaskProductList = from(workTaskProduct)
                .select(workTaskProduct)
                .where(predicate,dateRange)
                .orderBy(workTaskProduct.created.desc()) // WorkTaskProduct by created field in descending workTaskProduct
                .fetch();



        return workTaskProductList;

    }

    @Override
    public List<WorkTaskProduct> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QWorkTaskProduct workTaskProduct = QWorkTaskProduct.workTaskProduct;



        List<WorkTaskProduct> workTaskProductList = from(workTaskProduct)
                .select(workTaskProduct)
                .where()
                .fetch();

        return workTaskProductList;

    }

    @Override
    public List<WorkTaskProduct> findByWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto){
        QBom bom = QBom.bom;
        QWorkTask workTask = QWorkTask.workTask;
        QWorkTaskProduct workTaskProduct = QWorkTaskProduct.workTaskProduct;


        Long search_id = workTaskProductSearchDto.getWork_task_uid();

        Predicate work_task_uid = workTask.uid.eq(search_id);
        List<Tuple> results = from(workTaskProduct)
                .leftJoin(workTaskProduct.workTask, workTask).fetchJoin()
                .leftJoin(workTaskProduct.bom, bom).fetchJoin()
                .select(workTaskProduct,workTask,bom)
                .where(work_task_uid)
                .fetch();
        List<WorkTaskProduct> workTaskProductList = new ArrayList<>();
        for (Tuple result : results) {
            WorkTaskProduct workTaskProductEntity = result.get(workTaskProduct);
            workTaskProductList.add(workTaskProductEntity);
        }
        return workTaskProductList;
    }
}
