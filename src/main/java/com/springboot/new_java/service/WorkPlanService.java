package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workPlan.WorkPlanDto;
import com.springboot.new_java.data.entity.WorkPlan;
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
