package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.bom.BomDto;
import com.springboot.java_eco.data.entity.Bom;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BomService {

    List<Bom> getTotalBom(CommonInfoSearchDto commonInfoSearchDto);

    List<Bom> getBom(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveBom(BomDto bomDto) throws Exception;

    CommonResultDto updateBom(BomDto bomDto) throws Exception;


    String deleteBom(List<Map<String, Object>> requestList) throws Exception;

    void excelUploadBom(List<Map<String, Object>> requestList) throws Exception;


}
