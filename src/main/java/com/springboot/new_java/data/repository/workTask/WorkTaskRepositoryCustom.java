package com.springboot.new_java.data.repository.workTask;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.WorkTask;

import java.util.List;

public interface WorkTaskRepositoryCustom {


    List<WorkTask> findAll(CommonSearchDto commonSearchDto);
    List<WorkTask> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
