package com.springboot.new_java.data.repository.workTask;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.WorkTaskController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.QWorkTask;
import com.springboot.new_java.data.entity.WorkTask;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


        // DateTimeFormatter를 사용하여 형식을 지정 (YYYY-MM-DD HH:mm:ss 형식으로 변환)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

// work_start_date가 String 컬럼이라고 가정하고 해당 값을 비교
        String start_date_str = start_date.format(formatter);
        String end_date_str = end_date.format(formatter);


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

        LOGGER.info("msg : {}",start_date_str);
        LOGGER.info("msg2 : {}",end_date_str);
        //Predicate dateRange = workTask.created.between(start_date, end_date);
        Predicate dateRange = workTask.work_start_date.between(start_date_str, end_date_str); // work_start_date 컬럼이 String 타입이라 가정

        Predicate predicate = builder.getValue();

        List<WorkTask> workTaskList = from(workTask)
                .select(workTask)
                .where(predicate,dateRange)
                .orderBy(workTask.work_start_date.desc()) // WorkTask by created field in descending workTask
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
