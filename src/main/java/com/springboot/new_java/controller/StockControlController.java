package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;

import com.springboot.new_java.data.dto.stockControl.StockControlDto;
import com.springboot.new_java.data.entity.StockControl;
import com.springboot.new_java.service.StockControlService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock_control")
@Controller
public class StockControlController {
    private final StockControlService stockControlService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockControlController.class);

    @Autowired
    public StockControlController(StockControlService stockControlService){
        this.stockControlService = stockControlService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<StockControl>> getStockControl(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockControl> selectedStockControl = stockControlService.getStockControl(commonInfoSearchDto);

        LOGGER.info("[getTotalStockControlSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedStockControl);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<StockControl>> getTotalStockControl(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockControl> selectedTotalStockControl = stockControlService.getTotalStockControl(commonSearchDto);

        LOGGER.info("[getTotalStockControl] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockControl);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createStockControl(@RequestBody StockControlDto stockControlDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[stockControlDto]  : {}", stockControlDto);
        CommonResultDto stockControlResultDto = stockControlService.saveStockControl(stockControlDto);
        LOGGER.info("[createStockControl] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  stockControlResultDto;

    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteStockControl(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        stockControlService.deleteStockControl(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
