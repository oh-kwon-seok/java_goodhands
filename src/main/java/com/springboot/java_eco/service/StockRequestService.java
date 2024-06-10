package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.java_eco.data.entity.StockRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockRequestService {

    List<StockRequest> getStockRequest(CommonInfoSearchDto commonInfoSearchDto);

    List<StockRequest> getTotalStockRequest(CommonSearchDto commonSearchDto);


    List<StockRequest> getWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto);


}
