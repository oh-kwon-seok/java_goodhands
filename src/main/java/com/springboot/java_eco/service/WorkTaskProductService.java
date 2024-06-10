package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkTaskProductService {

    List<WorkTaskProduct> getWorkTaskProduct(CommonInfoSearchDto commonInfoSearchDto);

    List<WorkTaskProduct> getTotalWorkTaskProduct(CommonSearchDto commonSearchDto);


    List<WorkTaskProduct> getWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto);


}
