package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.BomDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.bom.BomDto;
import com.springboot.java_eco.data.entity.Bom;
import com.springboot.java_eco.service.BomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BomServiceImpl implements BomService {
    private final BomDAO bomDAO;

    @Autowired
    public BomServiceImpl(@Qualifier("bomDAOImpl") BomDAO bomDAO){
        this.bomDAO = bomDAO;
    }


    @Override
    public List<Bom> getTotalBom(CommonInfoSearchDto commonInfoSearchDto){
        return bomDAO.selectTotalBom(commonInfoSearchDto);
    }

    @Override
    public List<Bom> getBom(CommonInfoSearchDto commonInfoSearchDto){
        return bomDAO.selectBom(commonInfoSearchDto);
    }
    @Override
    public CommonResultDto saveBom(BomDto bomDto) throws Exception {

        return bomDAO.insertBom(bomDto);

    }
    @Override
    public CommonResultDto updateBom(BomDto bomDto) throws Exception {
        return bomDAO.updateBom(bomDto);
    }
    @Override
    public void deleteBom(List<Map<String, Object>> requestList) throws Exception {
        bomDAO.deleteBom(requestList);
    }
    @Override
    public void excelUploadBom(List<Map<String, Object>> requestList) throws Exception {
        bomDAO.excelUploadBom(requestList);
    }

}
