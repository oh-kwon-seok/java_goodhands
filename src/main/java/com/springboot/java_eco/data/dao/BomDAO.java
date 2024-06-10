package com.springboot.java_eco.data.dao;


import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.bom.BomDto;
import com.springboot.java_eco.data.entity.Bom;

import java.util.List;
import java.util.Map;


public interface BomDAO {
    CommonResultDto insertBom(BomDto bomDto);

    List<Bom> selectTotalBom(CommonInfoSearchDto commonInfoSearchDto);
    List<Bom> selectBom(CommonInfoSearchDto commonInfoSearchDto);

    CommonResultDto updateBom(BomDto bomDto) throws Exception;

    //String deleteBom(List<Long> uid) throws Exception;
    String deleteBom(List<Map<String, Object>> requestList) throws Exception;

    String excelUploadBom(List<Map<String, Object>> requestList) throws Exception;


}
