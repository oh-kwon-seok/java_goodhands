package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.WorkTaskDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;

import com.springboot.new_java.data.dto.workTask.WorkTaskDto;
import com.springboot.new_java.data.entity.WorkTask;
import com.springboot.new_java.service.WorkTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTaskServiceImpl implements WorkTaskService {
    private final WorkTaskDAO workTaskDAO;

    @Autowired
    public WorkTaskServiceImpl(@Qualifier("workTaskDAOImpl") WorkTaskDAO workTaskDAO){
        this.workTaskDAO = workTaskDAO;
    }

    @Override
    public List<WorkTask> getWorkTask(CommonInfoSearchDto commonInfoSearchDto){
        return workTaskDAO.selectWorkTask(commonInfoSearchDto);
    }
    @Override
    public List<WorkTask> getTotalWorkTask(CommonSearchDto commonSearchDto){
        return workTaskDAO.selectTotalWorkTask(commonSearchDto);
    }
    @Override
    public CommonResultDto saveWorkTask(WorkTaskDto workPlanDto) throws Exception {

        return workTaskDAO.insertWorkTask(workPlanDto);

    }
    @Override
    public CommonResultDto updateWorkTask(WorkTaskDto workPlanDto) throws Exception {

        return workTaskDAO.updateWorkTask(workPlanDto);

    }
    @Override
    public CommonResultDto approvalWorkTask(WorkTaskDto workPlanDto) throws Exception {

        return workTaskDAO.approvalWorkTask(workPlanDto);

    }
    @Override
    public CommonResultDto measureWorkTask(WorkTaskDto workPlanDto) throws Exception {

        return workTaskDAO.measureWorkTask(workPlanDto);

    }
    @Override
    public CommonResultDto productionWorkTask(WorkTaskDto workPlanDto) throws Exception {

        return workTaskDAO.productionWorkTask(workPlanDto);

    }
    @Override
    public CommonResultDto packingWorkTask(WorkTaskDto workPlanDto) throws Exception {

        return workTaskDAO.packingWorkTask(workPlanDto);

    }


    @Override
    public void deleteWorkTask(List<Long> uid) throws Exception {
        workTaskDAO.deleteWorkTask(uid);
    }


}
