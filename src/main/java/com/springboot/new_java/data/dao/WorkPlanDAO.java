package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workPlan.WorkPlanDto;
import com.springboot.new_java.data.entity.WorkPlan;

import java.util.List;


public interface WorkPlanDAO {


    List<WorkPlan> selectWorkPlan(CommonInfoSearchDto commonInfoSearchDto);
    List<WorkPlan> selectTotalWorkPlan(CommonSearchDto commonSearchDto);

     CommonResultDto insertWorkPlan(WorkPlanDto workPlanDto)  throws Exception;

    CommonResultDto updateWorkPlan(WorkPlanDto workPlanDto)  throws Exception;
    String deleteWorkPlan(List<Long> uid) throws Exception;



}
