package com.springboot.new_java.data.repository.stockRequest;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.new_java.data.entity.StockRequest;

import java.util.List;

public interface StockRequestRepositoryCustom {


    List<StockRequest> findAll(CommonSearchDto commonSearchDto);
    List<StockRequest> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<StockRequest> findByWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto);

}
