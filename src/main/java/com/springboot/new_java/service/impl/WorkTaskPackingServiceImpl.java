package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.WorkTaskPackingDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.new_java.data.entity.WorkTaskPacking;
import com.springboot.new_java.service.WorkTaskPackingService;
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
