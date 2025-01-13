package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.new_java.data.entity.WorkTaskProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkTaskProductService {

    List<WorkTaskProduct> getWorkTaskProduct(CommonInfoSearchDto commonInfoSearchDto);

    List<WorkTaskProduct> getTotalWorkTaskProduct(CommonSearchDto commonSearchDto);


    List<WorkTaskProduct> getWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto);


}
