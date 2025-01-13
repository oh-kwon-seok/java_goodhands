package com.springboot.new_java.data.repository.estimate;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.Estimate;

import java.util.List;

public interface EstimateRepositoryCustom {


    List<Estimate> findAll(CommonSearchDto commonSearchDto);
    List<Estimate> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
