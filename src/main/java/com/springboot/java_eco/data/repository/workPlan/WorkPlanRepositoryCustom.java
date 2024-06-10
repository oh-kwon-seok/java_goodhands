package com.springboot.java_eco.data.repository.workPlan;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.WorkPlan;

import java.util.List;

public interface WorkPlanRepositoryCustom {


    List<WorkPlan> findAll(CommonSearchDto commonSearchDto);
    List<WorkPlan> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
