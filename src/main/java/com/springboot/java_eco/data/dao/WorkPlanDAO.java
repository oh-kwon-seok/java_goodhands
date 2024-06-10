package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workPlan.WorkPlanDto;
import com.springboot.java_eco.data.entity.WorkPlan;

import java.util.List;


public interface WorkPlanDAO {


    List<WorkPlan> selectWorkPlan(CommonInfoSearchDto commonInfoSearchDto);
    List<WorkPlan> selectTotalWorkPlan(CommonSearchDto commonSearchDto);

     CommonResultDto insertWorkPlan(WorkPlanDto workPlanDto)  throws Exception;

    CommonResultDto updateWorkPlan(WorkPlanDto workPlanDto)  throws Exception;
    String deleteWorkPlan(List<Long> uid) throws Exception;



}
