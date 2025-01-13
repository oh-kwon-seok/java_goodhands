package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.factorySub.FactorySubSearchDto;
import com.springboot.new_java.data.entity.FactorySub;
import com.springboot.new_java.service.FactorySubService;

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
@RequestMapping("/factory_sub")
@Controller
public class FactorySubController {
    private final FactorySubService factorySubService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(FactorySubController.class);

    @Autowired
    public FactorySubController(FactorySubService factorySubService){
        this.factorySubService = factorySubService;
    }

    @GetMapping(value= "/select") // 매입처별/품목별 매입,  품목별/상품별 매입단가 조회
    public ResponseEntity<List<FactorySub>> getTotalFactorySub(@ModelAttribute FactorySubSearchDto factorySubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();
        List<FactorySub> selectedTotalFactorySub = factorySubService.getTotalFactorySub(factorySubSearchDto);

        LOGGER.info("[getTotalUserOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalFactorySub);
    }



    @GetMapping(value= "/info_select")
    public ResponseEntity<List<FactorySub>> getFactorySub(@ModelAttribute FactorySubSearchDto factorySubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<FactorySub> selectedTotalFactorySub = factorySubService.getFactorySub(factorySubSearchDto);

        LOGGER.info("[getTotalFactorySub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalFactorySub);

    }
    @GetMapping(value= "/info_total_select")
    public ResponseEntity<List<FactorySub>> getTotalInfoFactorySub(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<FactorySub> selectedInfoTotalFactorySub = factorySubService.getTotalInfoFactorySub(commonInfoSearchDto);

        LOGGER.info("[getTotalFactorySub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedInfoTotalFactorySub);

    }



}
