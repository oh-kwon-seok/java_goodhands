package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockRecord.StockRecordDto;
import com.springboot.new_java.data.entity.StockRecord;
import com.springboot.new_java.service.StockRecordService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock_record")
@Controller
public class StockRecordController {
    private final StockRecordService stockRecordService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockRecordController.class);

    @Autowired
    public StockRecordController(StockRecordService stockRecordService){
        this.stockRecordService = stockRecordService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<StockRecord>> getStockRecord(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockRecord> selectedStockRecord = stockRecordService.getStockRecord(commonInfoSearchDto);

        LOGGER.info("[getTotalStockRecordSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedStockRecord);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<StockRecord>> getTotalStockRecord(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockRecord> selectedTotalStockRecord = stockRecordService.getTotalStockRecord(commonSearchDto);

        LOGGER.info("[getTotalStockRecord] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockRecord);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createStockRecord(@RequestBody StockRecordDto stockRecordDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[stockRecordDto]  : {}", stockRecordDto);
        CommonResultDto stockRecordResultDto = stockRecordService.saveStockRecord(stockRecordDto);
        LOGGER.info("[createStockRecord] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  stockRecordResultDto;

    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        stockRecordService.deleteStockRecord(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
