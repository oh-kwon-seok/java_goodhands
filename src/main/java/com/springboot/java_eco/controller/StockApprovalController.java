package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.java_eco.data.entity.StockApproval;
import com.springboot.java_eco.service.StockApprovalService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock_approval")
@Controller
public class StockApprovalController {
    private final StockApprovalService stockApprovalService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockApprovalController.class);

    @Autowired
    public StockApprovalController(StockApprovalService stockApprovalService){
        this.stockApprovalService = stockApprovalService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<StockApproval>> getStockApproval(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockApproval> selectedStockApproval = stockApprovalService.getStockApproval(commonInfoSearchDto);

        LOGGER.info("[getTotalStockApprovalSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedStockApproval);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<StockApproval>> getTotalStockApproval(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockApproval> selectedTotalStockApproval = stockApprovalService.getTotalStockApproval(commonSearchDto);

        LOGGER.info("[getTotalStockApproval] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockApproval);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<StockApproval>> getWorkTaskUidSelect(@ModelAttribute StockApprovalSearchDto stockApprovalSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockApproval> selectWorkTaskUidSelect = stockApprovalService.getWorkTaskUidSelect(stockApprovalSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectWorkTaskUidSelect);

    }







}
