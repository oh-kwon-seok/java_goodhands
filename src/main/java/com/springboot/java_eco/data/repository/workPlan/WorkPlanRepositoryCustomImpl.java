package com.springboot.java_eco.data.repository.workPlan;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.WorkPlanController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.QWorkPlan;
import com.springboot.java_eco.data.entity.WorkPlan;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class WorkPlanRepositoryCustomImpl extends QuerydslRepositorySupport implements WorkPlanRepositoryCustom {

    public WorkPlanRepositoryCustomImpl(){
        super(WorkPlan.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkPlanController.class);


    @Override
    public List<WorkPlan> findAll(CommonSearchDto commonSearchDto){
        QWorkPlan workPlan = QWorkPlan.workPlan;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (workPlan.bom.item.ingr_eng_name != null) {
                builder.or(workPlan.bom.item.ingr_eng_name.like("%" + search_text + "%"));
            }

        }else {
           if("ingr_eng_name".equals(filter_title)){
                builder.and(workPlan.bom.item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }


        Predicate dateRange = workPlan.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();
        Predicate used = workPlan.used.eq(1L);

        List<WorkPlan> workPlanList = from(workPlan)
                .select(workPlan)
                .where(predicate,dateRange,used)
                .orderBy(workPlan.created.desc()) // WorkPlan by created field in descending workPlan
                .fetch();



        return workPlanList;

    }

    @Override
    public List<WorkPlan> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QWorkPlan workPlan = QWorkPlan.workPlan;
        List<WorkPlan> workPlanList = from(workPlan)
                .select(workPlan)
                .where()
                .orderBy(workPlan.code.desc(),workPlan.startDate.desc())
                .fetch();

        return workPlanList;

    }
}
