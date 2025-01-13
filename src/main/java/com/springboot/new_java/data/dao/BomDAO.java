package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.bom.BomDto;
import com.springboot.new_java.data.entity.Bom;

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
