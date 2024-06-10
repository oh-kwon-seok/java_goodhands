package com.springboot.java_eco.data.repository.workTaskProduct;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskProduct;

import java.util.List;

public interface WorkTaskProductRepositoryCustom {


    List<WorkTaskProduct> findAll(CommonSearchDto commonSearchDto);
    List<WorkTaskProduct> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<WorkTaskProduct> findByWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto);

}
