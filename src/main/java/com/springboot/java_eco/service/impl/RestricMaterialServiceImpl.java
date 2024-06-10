package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.RestricMaterialDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.restricMaterial.RestricMaterialDto;
import com.springboot.java_eco.data.entity.RestricMaterial;
import com.springboot.java_eco.service.RestricMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestricMaterialServiceImpl implements RestricMaterialService {
    private final RestricMaterialDAO restricMaterialDAO;

    @Autowired
    public RestricMaterialServiceImpl(@Qualifier("restricMaterialDAOImpl") RestricMaterialDAO restricMaterialDAO){
        this.restricMaterialDAO = restricMaterialDAO;
    }


    @Override
    public List<RestricMaterial> getTotalRestricMaterial(CommonInfoSearchDto commonInfoSearchDto){
        return restricMaterialDAO.selectTotalRestricMaterial(commonInfoSearchDto);
    }

    @Override
    public List<RestricMaterial> getRestricMaterial(CommonInfoSearchDto commonInfoSearchDto){
        return restricMaterialDAO.selectRestricMaterial(commonInfoSearchDto);
    }
    @Override
    public CommonResultDto saveRestricMaterial(RestricMaterialDto restricMaterialDto,String clientIp) throws Exception {

        return restricMaterialDAO.insertRestricMaterial(restricMaterialDto,clientIp);

    }

    @Override
    public void deleteRestricMaterial(List<Long> uid) throws Exception {
        restricMaterialDAO.deleteRestricMaterial(uid);
    }


}
