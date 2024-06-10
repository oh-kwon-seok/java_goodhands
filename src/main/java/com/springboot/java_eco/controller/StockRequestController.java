package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;
import com.springboot.java_eco.data.entity.StockRequest;
import com.springboot.java_eco.service.StockRequestService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock_request")
@Controller
public class StockRequestController {
    private final StockRequestService stockRequestService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockRequestController.class);

    @Autowired
    public StockRequestController(StockRequestService stockRequestService){
        this.stockRequestService = stockRequestService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<StockRequest>> getStockRequest(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockRequest> selectedStockRequest = stockRequestService.getStockRequest(commonInfoSearchDto);

        LOGGER.info("[getTotalStockRequestSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedStockRequest);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<StockRequest>> getTotalStockRequest(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockRequest> selectedTotalStockRequest = stockRequestService.getTotalStockRequest(commonSearchDto);

        LOGGER.info("[getTotalStockRequest] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockRequest);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<StockRequest>> getWorkTaskUidSelect(@ModelAttribute StockRequestSearchDto stockRequestSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockRequest> selectWorkTaskUidSelect = stockRequestService.getWorkTaskUidSelect(stockRequestSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectWorkTaskUidSelect);

    }







}
