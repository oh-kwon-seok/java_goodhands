package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.new_java.data.entity.StockInoutSub;
import com.springboot.new_java.service.StockInoutSubService;
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
@RequestMapping("/stock_inout_sub")
@Controller
public class StockInoutSubController {
    private final StockInoutSubService stockInoutSubService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockInoutSubController.class);

    @Autowired
    public StockInoutSubController(StockInoutSubService stockInoutSubService){
        this.stockInoutSubService = stockInoutSubService;
    }

    @GetMapping(value= "/select") // 매입처별/품목별 매입,  품목별/상품별 매입단가 조회
    public ResponseEntity<List<StockInoutSub>> getTotalStockInoutSub(@ModelAttribute StockInoutSubSearchDto stockInoutSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();
        List<StockInoutSub> selectedTotalStockInoutSub = stockInoutSubService.getTotalStockInoutSub(stockInoutSubSearchDto);

        LOGGER.info("[getTotalUserOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockInoutSub);
    }



    @GetMapping(value= "/info_select")
    public ResponseEntity<List<StockInoutSub>> getStockInoutSub(@ModelAttribute StockInoutSubSearchDto stockInoutSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockInoutSub> selectedTotalStockInoutSub = stockInoutSubService.getStockInoutSub(stockInoutSubSearchDto);

        LOGGER.info("[getTotalStockInoutSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockInoutSub);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<StockInoutSub>> getStockInoutUidSelect(@ModelAttribute StockInoutSubSearchDto stockInoutSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockInoutSub> selectStockInoutUidSelect = stockInoutSubService.getStockInoutUidSelect(stockInoutSubSearchDto);

        LOGGER.info("[getTotalStockInoutSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectStockInoutUidSelect);

    }


}
