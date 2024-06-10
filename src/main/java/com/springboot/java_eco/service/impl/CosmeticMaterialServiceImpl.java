package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.CosmeticMaterialDAO;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.costmeticMaterial.CosmeticMaterialDto;
import com.springboot.java_eco.data.entity.CosmeticMaterial;
import com.springboot.java_eco.service.CosmeticMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmeticMaterialServiceImpl implements CosmeticMaterialService {
    private final CosmeticMaterialDAO cosmeticMaterialDAO;

    @Autowired
    public CosmeticMaterialServiceImpl(@Qualifier("cosmeticMaterialDAOImpl") CosmeticMaterialDAO cosmeticMaterialDAO){
        this.cosmeticMaterialDAO = cosmeticMaterialDAO;
    }


    @Override
    public List<CosmeticMaterial> getTotalCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto){
        return cosmeticMaterialDAO.selectTotalCosmeticMaterial(commonInfoSearchDto);
    }

    @Override
    public List<CosmeticMaterial> getCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto){
        return cosmeticMaterialDAO.selectCosmeticMaterial(commonInfoSearchDto);
    }
    @Override
    public CommonResultDto saveCosmeticMaterial(CosmeticMaterialDto cosmeticMaterialDto, String clientIp) throws Exception {

        return cosmeticMaterialDAO.insertCosmeticMaterial(cosmeticMaterialDto,clientIp);

    }

    @Override
    public void deleteCosmeticMaterial(List<Long> uid) throws Exception {
        cosmeticMaterialDAO.deleteCosmeticMaterial(uid);
    }


}
