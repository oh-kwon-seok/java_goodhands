package com.springboot.new_java.data.repository.workTaskProduct;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.new_java.data.entity.WorkTaskProduct;

import java.util.List;

public interface WorkTaskProductRepositoryCustom {


    List<WorkTaskProduct> findAll(CommonSearchDto commonSearchDto);
    List<WorkTaskProduct> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<WorkTaskProduct> findByWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto);

}
