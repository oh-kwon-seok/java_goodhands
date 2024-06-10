package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.WorkTaskPackingDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskPacking;
import com.springboot.java_eco.service.WorkTaskPackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTaskPackingServiceImpl implements WorkTaskPackingService {
    private final WorkTaskPackingDAO workTaskPackingDAO;

    @Autowired
    public WorkTaskPackingServiceImpl(@Qualifier("workTaskPackingDAOImpl") WorkTaskPackingDAO workTaskPackingDAO){
        this.workTaskPackingDAO = workTaskPackingDAO;
    }

    @Override
    public List<WorkTaskPacking> getWorkTaskPacking(CommonInfoSearchDto commonInfoSearchDto){
        return workTaskPackingDAO.selectWorkTaskPacking(commonInfoSearchDto);
    }
    @Override
    public List<WorkTaskPacking> getTotalWorkTaskPacking(CommonSearchDto commonSearchDto){
        return workTaskPackingDAO.selectTotalWorkTaskPacking(commonSearchDto);
    }

    @Override
    public List<WorkTaskPacking> getWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto){
        return workTaskPackingDAO.selectWorkTaskUidSelect(workTaskPackingSearchDto);
    }



}
