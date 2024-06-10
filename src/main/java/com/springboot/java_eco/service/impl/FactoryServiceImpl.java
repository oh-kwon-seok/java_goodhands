package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.FactoryDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.factory.FactoryDto;
import com.springboot.java_eco.data.entity.Factory;
import com.springboot.java_eco.service.FactoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {
    private final FactoryDAO factoryDAO;

    @Autowired
    public FactoryServiceImpl(@Qualifier("factoryDAOImpl") FactoryDAO factoryDAO){
        this.factoryDAO = factoryDAO;
    }

    @Override
    public List<Factory> getFactory(CommonInfoSearchDto commonInfoSearchDto){
        return factoryDAO.selectFactory(commonInfoSearchDto);
    }
    @Override
    public List<Factory> getTotalFactory(CommonInfoSearchDto commonInfoSearchDto){
        return factoryDAO.selectTotalFactory(commonInfoSearchDto);
    }
    @Override
    public CommonResultDto saveFactory(FactoryDto factoryDto) throws Exception {

        return factoryDAO.insertFactory(factoryDto);

    }
    @Override
    public CommonResultDto updateFactory(FactoryDto factoryDto) throws Exception {
        return factoryDAO.updateFactory(factoryDto);
    }
    @Override
    public void deleteFactory(List<Long> uid) throws Exception {
        factoryDAO.deleteFactory(uid);
    }

}
