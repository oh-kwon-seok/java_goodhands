package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTask.WorkTaskDto;
import com.springboot.java_eco.data.entity.WorkTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkTaskService {

    List<WorkTask> getWorkTask(CommonInfoSearchDto commonInfoSearchDto);

    List<WorkTask> getTotalWorkTask(CommonSearchDto commonSearchDto);


    CommonResultDto saveWorkTask(WorkTaskDto workTaskDto) throws Exception;
    CommonResultDto updateWorkTask(WorkTaskDto workTaskDto) throws Exception;
    CommonResultDto approvalWorkTask(WorkTaskDto workTaskDto) throws Exception;

    CommonResultDto measureWorkTask(WorkTaskDto workTaskDto) throws Exception;
    CommonResultDto productionWorkTask(WorkTaskDto workTaskDto) throws Exception;
    CommonResultDto packingWorkTask(WorkTaskDto workTaskDto) throws Exception;

    void deleteWorkTask(List<Long> uid) throws Exception;


}
