package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.WorkPlanDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workPlan.WorkPlanDto;
import com.springboot.new_java.data.entity.WorkPlan;
import com.springboot.new_java.service.WorkPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPlanServiceImpl implements WorkPlanService {
    private final WorkPlanDAO workPlanDAO;

    @Autowired
    public WorkPlanServiceImpl(@Qualifier("workPlanDAOImpl") WorkPlanDAO workPlanDAO){
        this.workPlanDAO = workPlanDAO;
    }

    @Override
    public List<WorkPlan> getWorkPlan(CommonInfoSearchDto commonInfoSearchDto){
        return workPlanDAO.selectWorkPlan(commonInfoSearchDto);
    }
    @Override
    public List<WorkPlan> getTotalWorkPlan(CommonSearchDto commonSearchDto){
        return workPlanDAO.selectTotalWorkPlan(commonSearchDto);
    }
    @Override
    public CommonResultDto saveWorkPlan(WorkPlanDto workPlanDto) throws Exception {

        return workPlanDAO.insertWorkPlan(workPlanDto);

    }
    @Override
    public CommonResultDto updateWorkPlan(WorkPlanDto workPlanDto) throws Exception {

        return workPlanDAO.updateWorkPlan(workPlanDto);

    }

    @Override
    public void deleteWorkPlan(List<Long> uid) throws Exception {
        workPlanDAO.deleteWorkPlan(uid);
    }


}
