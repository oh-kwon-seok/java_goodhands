package com.springboot.new_java.data.repository.workPlan;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.WorkPlan;

import java.util.List;

public interface WorkPlanRepositoryCustom {


    List<WorkPlan> findAll(CommonSearchDto commonSearchDto);
    List<WorkPlan> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
