package com.springboot.new_java.data.repository.workTaskPacking;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.WorkTaskPackingController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.new_java.data.entity.QBom;
import com.springboot.new_java.data.entity.QWorkTask;
import com.springboot.new_java.data.entity.QWorkTaskPacking;
import com.springboot.new_java.data.entity.WorkTaskPacking;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkTaskPackingRepositoryCustomImpl extends QuerydslRepositorySupport implements WorkTaskPackingRepositoryCustom {

    public WorkTaskPackingRepositoryCustomImpl(){
        super(WorkTaskPacking.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskPackingController.class);

    @Override
    public List<WorkTaskPacking> findAll(CommonSearchDto commonSearchDto){
        QWorkTaskPacking workTaskPacking = QWorkTaskPacking.workTaskPacking;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (workTaskPacking.bom.code != null) {
                builder.or(workTaskPacking.bom.code.like("%" + search_text + "%"));
            }



        }else {

            if("code".equals(filter_title)){
                builder.and(workTaskPacking.bom.code.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가


        Predicate dateRange = workTaskPacking.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<WorkTaskPacking> workTaskPackingList = from(workTaskPacking)
                .select(workTaskPacking)
                .where(predicate,dateRange)
                .orderBy(workTaskPacking.created.desc()) // WorkTaskPacking by created field in descending workTaskPacking
                .fetch();



        return workTaskPackingList;

    }

    @Override
    public List<WorkTaskPacking> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QWorkTaskPacking workTaskPacking = QWorkTaskPacking.workTaskPacking;



        List<WorkTaskPacking> workTaskPackingList = from(workTaskPacking)
                .select(workTaskPacking)
                .where()
                .fetch();

        return workTaskPackingList;

    }

    @Override
    public List<WorkTaskPacking> findByWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto){
        QBom bom = QBom.bom;
        QWorkTask workTask = QWorkTask.workTask;
        QWorkTaskPacking workTaskPacking = QWorkTaskPacking.workTaskPacking;


        Long search_id = workTaskPackingSearchDto.getWork_task_uid();

        Predicate work_task_uid = workTask.uid.eq(search_id);
        List<Tuple> results = from(workTaskPacking)
                .leftJoin(workTaskPacking.workTask, workTask).fetchJoin()
                .leftJoin(workTaskPacking.bom, bom).fetchJoin()
                .select(workTaskPacking,workTask,bom)
                .where(work_task_uid)
                .fetch();
        List<WorkTaskPacking> workTaskPackingList = new ArrayList<>();
        for (Tuple result : results) {
            WorkTaskPacking workTaskPackingEntity = result.get(workTaskPacking);
            workTaskPackingList.add(workTaskPackingEntity);
        }
        return workTaskPackingList;
    }
}
