package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.WorkTaskProductDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskProduct;
import com.springboot.java_eco.service.WorkTaskProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTaskProductServiceImpl implements WorkTaskProductService {
    private final WorkTaskProductDAO workTaskProductDAO;

    @Autowired
    public WorkTaskProductServiceImpl(@Qualifier("workTaskProductDAOImpl") WorkTaskProductDAO workTaskProductDAO){
        this.workTaskProductDAO = workTaskProductDAO;
    }

    @Override
    public List<WorkTaskProduct> getWorkTaskProduct(CommonInfoSearchDto commonInfoSearchDto){
        return workTaskProductDAO.selectWorkTaskProduct(commonInfoSearchDto);
    }
    @Override
    public List<WorkTaskProduct> getTotalWorkTaskProduct(CommonSearchDto commonSearchDto){
        return workTaskProductDAO.selectTotalWorkTaskProduct(commonSearchDto);
    }

    @Override
    public List<WorkTaskProduct> getWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto){
        return workTaskProductDAO.selectWorkTaskUidSelect(workTaskProductSearchDto);
    }



}
