package com.springboot.java_eco.data.dao.impl;

import com.springboot.java_eco.data.dao.FactorySubDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.factorySub.FactorySubSearchDto;
import com.springboot.java_eco.data.entity.FactorySub;
import com.springboot.java_eco.data.repository.factorySub.FactorySubRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FactorySubDAOImpl implements FactorySubDAO {
    
    private final FactorySubRepository factorySubRepository;
    @Autowired
    public FactorySubDAOImpl(FactorySubRepository factorySubRepository){
        this.factorySubRepository = factorySubRepository;

    }

    @Override
    public List<FactorySub> selectTotalFactorySub(FactorySubSearchDto factorySubSearchDto) {
        return factorySubRepository.findAll(factorySubSearchDto);

    }


    @Override
    public List<FactorySub> selectFactorySub(FactorySubSearchDto factorySubSearchDto) {
        return factorySubRepository.findInfo(factorySubSearchDto);

    }
    @Override
    public List<FactorySub> selectTotalInfoFactorySub(CommonInfoSearchDto commonInfoSearchDto) {
        return factorySubRepository.findInfoFactorySub(commonInfoSearchDto);

    }
}
