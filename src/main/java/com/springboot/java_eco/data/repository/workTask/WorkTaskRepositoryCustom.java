package com.springboot.java_eco.data.repository.workTask;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.WorkTask;

import java.util.List;

public interface WorkTaskRepositoryCustom {


    List<WorkTask> findAll(CommonSearchDto commonSearchDto);
    List<WorkTask> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
