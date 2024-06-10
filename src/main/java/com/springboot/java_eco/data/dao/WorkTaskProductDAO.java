package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskProduct;

import java.util.List;


public interface WorkTaskProductDAO {


    List<WorkTaskProduct> selectWorkTaskProduct(CommonInfoSearchDto commonInfoSearchDto);
    List<WorkTaskProduct> selectTotalWorkTaskProduct(CommonSearchDto commonSearchDto);

    List<WorkTaskProduct> selectWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto);

}
