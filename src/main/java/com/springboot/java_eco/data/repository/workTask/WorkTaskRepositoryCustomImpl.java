package com.springboot.java_eco.data.repository.workTask;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.WorkTaskController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.QWorkTask;
import com.springboot.java_eco.data.entity.WorkTask;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class WorkTaskRepositoryCustomImpl extends QuerydslRepositorySupport implements WorkTaskRepositoryCustom {

    public WorkTaskRepositoryCustomImpl(){
        super(WorkTask.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskController.class);


    @Override
    public List<WorkTask> findAll(CommonSearchDto commonSearchDto){
        QWorkTask workTask = QWorkTask.workTask;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (workTask.bom.item.ingr_eng_name != null) {
                builder.or(workTask.bom.item.ingr_eng_name.like("%" + search_text + "%"));
            }

        }else {
           if("ingr_eng_name".equals(filter_title)){
                builder.and(workTask.bom.item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }


        Predicate dateRange = workTask.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<WorkTask> workTaskList = from(workTask)
                .select(workTask)
                .where(predicate,dateRange)
                .orderBy(workTask.created.desc()) // WorkTask by created field in descending workTask
                .fetch();
        return workTaskList;

    }

    @Override
    public List<WorkTask> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QWorkTask workTask = QWorkTask.workTask;
        List<WorkTask> workTaskList = from(workTask)
                .select(workTask)
                .where()
                .fetch();
        return workTaskList;

    }
}
