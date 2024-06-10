package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockInout.StockInoutDto;
import com.springboot.java_eco.data.entity.StockInout;
import com.springboot.java_eco.service.StockInoutService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock_inout")
@Controller
public class StockInoutController {
    private final StockInoutService stockInoutService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockInoutController.class);

    @Autowired
    public StockInoutController(StockInoutService stockInoutService){
        this.stockInoutService = stockInoutService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<StockInout>> getStockInout(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockInout> selectedStockInout = stockInoutService.getStockInout(commonInfoSearchDto);

        LOGGER.info("[getTotalStockInoutSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedStockInout);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<StockInout>> getTotalStockInout(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockInout> selectedTotalStockInout = stockInoutService.getTotalStockInout(commonSearchDto);

        LOGGER.info("[getTotalStockInout] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockInout);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createStockInout(@RequestBody StockInoutDto stockInoutDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[stockInoutDto]  : {}", stockInoutDto);
        CommonResultDto stockInoutResultDto = stockInoutService.saveStockInout(stockInoutDto);
        LOGGER.info("[createStockInout] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  stockInoutResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateStockInout(@RequestBody StockInoutDto stockInoutDto)
            throws Exception{
        CommonResultDto stockInoutResultDto = stockInoutService.updateStockInout(stockInoutDto);
        return stockInoutResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        stockInoutService.deleteStockInout(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
