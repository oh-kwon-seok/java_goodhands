package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stock.LotSearchDto;
import com.springboot.java_eco.data.dto.stock.StockDto;
import com.springboot.java_eco.data.entity.Stock;
import com.springboot.java_eco.service.StockService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
@Controller
public class StockController {
    private final StockService stockService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockController.class);

    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Stock>> getStock(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Stock> selectedStock = stockService.getStock(commonInfoSearchDto);

        LOGGER.info("[getTotalStockSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedStock);

    }

    @GetMapping(value= "/lot_select")
    public ResponseEntity<List<Stock>> getLotStock(@ModelAttribute LotSearchDto lotSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Stock> selectedLotStock = stockService.getLotStock(lotSearchDto);

        LOGGER.info("[getTotalStockSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedLotStock);

    }
    @GetMapping(value= "/packing_lot_select")
    public ResponseEntity<List<Stock>> getPackingLotStock(@ModelAttribute LotSearchDto lotSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Stock> selectedPackingLotStock = stockService.getPackingLotStock(lotSearchDto);

        LOGGER.info("[getTotalStockSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedPackingLotStock);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<Stock>> getTotalStock(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Stock> selectedTotalStock = stockService.getTotalStock(commonSearchDto);

        LOGGER.info("[getTotalStock] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStock);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createStock(@RequestBody StockDto stockDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[stockDto]  : {}", stockDto);
        CommonResultDto stockResultDto = stockService.saveStock(stockDto);
        LOGGER.info("[createStock] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  stockResultDto;

    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        stockService.deleteStock(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
