package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.FactoryDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.factory.FactoryDto;
import com.springboot.new_java.data.entity.Factory;
import com.springboot.new_java.service.FactoryService;

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
