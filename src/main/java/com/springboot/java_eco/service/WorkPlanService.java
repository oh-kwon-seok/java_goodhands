package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workPlan.WorkPlanDto;
import com.springboot.java_eco.data.entity.WorkPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkPlanService {

    List<WorkPlan> getWorkPlan(CommonInfoSearchDto commonInfoSearchDto);

    List<WorkPlan> getTotalWorkPlan(CommonSearchDto commonSearchDto);


    CommonResultDto saveWorkPlan(WorkPlanDto workPlanDto) throws Exception;
    CommonResultDto updateWorkPlan(WorkPlanDto workPlanDto) throws Exception;


    void deleteWorkPlan(List<Long> uid) throws Exception;


}
