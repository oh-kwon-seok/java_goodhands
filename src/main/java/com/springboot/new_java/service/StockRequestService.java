package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.new_java.data.entity.StockRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockRequestService {

    List<StockRequest> getStockRequest(CommonInfoSearchDto commonInfoSearchDto);

    List<StockRequest> getTotalStockRequest(CommonSearchDto commonSearchDto);


    List<StockRequest> getWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto);


}
