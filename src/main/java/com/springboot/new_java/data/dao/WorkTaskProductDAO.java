package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.new_java.data.entity.WorkTaskProduct;

import java.util.List;


public interface WorkTaskProductDAO {


    List<WorkTaskProduct> selectWorkTaskProduct(CommonInfoSearchDto commonInfoSearchDto);
    List<WorkTaskProduct> selectTotalWorkTaskProduct(CommonSearchDto commonSearchDto);

    List<WorkTaskProduct> selectWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto);

}
