package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.RestricMaterialCountryDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.restricMaterialCountry.RestricMaterialCountryDto;
import com.springboot.new_java.data.entity.RestricMaterialCountry;
import com.springboot.new_java.service.RestricMaterialCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestricMaterialCountryServiceImpl implements RestricMaterialCountryService {
    private final RestricMaterialCountryDAO restricMaterialCountryDAO;

    @Autowired
    public RestricMaterialCountryServiceImpl(@Qualifier("restricMaterialCountryDAOImpl") RestricMaterialCountryDAO restricMaterialCountryDAO){
        this.restricMaterialCountryDAO = restricMaterialCountryDAO;
    }


    @Override
    public List<RestricMaterialCountry> getTotalRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto){
        return restricMaterialCountryDAO.selectTotalRestricMaterialCountry(commonInfoSearchDto);
    }

    @Override
    public List<RestricMaterialCountry> getRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto){
        return restricMaterialCountryDAO.selectRestricMaterialCountry(commonInfoSearchDto);
    }
    @Override
    public CommonResultDto saveRestricMaterialCountry(RestricMaterialCountryDto restricMaterialCountryDto,String clientIp) throws Exception {

        return restricMaterialCountryDAO.insertRestricMaterialCountry(restricMaterialCountryDto,clientIp);

    }

    @Override
    public void deleteRestricMaterialCountry(List<Long> uid) throws Exception {
        restricMaterialCountryDAO.deleteRestricMaterialCountry(uid);
    }


}
