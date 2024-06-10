package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.RestricMaterialCountryDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.restricMaterialCountry.RestricMaterialCountryDto;
import com.springboot.java_eco.data.entity.RestricMaterialCountry;
import com.springboot.java_eco.service.RestricMaterialCountryService;
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
