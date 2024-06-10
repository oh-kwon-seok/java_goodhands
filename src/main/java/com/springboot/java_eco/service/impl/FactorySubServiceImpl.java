package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.FactorySubDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.factorySub.FactorySubSearchDto;
import com.springboot.java_eco.data.entity.FactorySub;
import com.springboot.java_eco.service.FactorySubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactorySubServiceImpl implements FactorySubService {
    private final FactorySubDAO factorySubDAO;

    @Autowired
    public FactorySubServiceImpl(@Qualifier("factorySubDAOImpl") FactorySubDAO factorySubDAO){
        this.factorySubDAO = factorySubDAO;
    }

    @Override
    public List<FactorySub> getTotalFactorySub(FactorySubSearchDto factorySubSearchDto){
        return factorySubDAO.selectTotalFactorySub(factorySubSearchDto);
    }

  

    @Override
    public List<FactorySub> getFactorySub(FactorySubSearchDto factorySubSearchDto){
        return factorySubDAO.selectFactorySub(factorySubSearchDto);
    }
    @Override
    public List<FactorySub> getTotalInfoFactorySub(CommonInfoSearchDto commonInfoSearchDto){
        return factorySubDAO.selectTotalInfoFactorySub(commonInfoSearchDto);
    }



}
