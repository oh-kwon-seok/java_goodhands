package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.factory.FactoryDto;
import com.springboot.java_eco.data.entity.Factory;
import com.springboot.java_eco.service.FactoryService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/factory")
@Controller
public class FactoryController {
    private final FactoryService factoryService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(FactoryController.class);

    @Autowired
    public FactoryController(FactoryService factoryService){
        this.factoryService = factoryService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Factory>> getFactory(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Factory> selectedFactory = factoryService.getFactory(commonInfoSearchDto);

        LOGGER.info("[getTotalFactorySub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedFactory);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<Factory>> getTotalFactory(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Factory> selectedTotalFactory = factoryService.getTotalFactory(commonInfoSearchDto);

        LOGGER.info("[getTotalFactory] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalFactory);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createFactory(@RequestBody FactoryDto factoryDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[factoryDto]  : {}", factoryDto);
        CommonResultDto factoryResultDto = factoryService.saveFactory(factoryDto);
        LOGGER.info("[createFactory] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  factoryResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateFactory(@RequestBody FactoryDto factoryDto)
            throws Exception{


        CommonResultDto factoryResultDto = factoryService.updateFactory(factoryDto);
        return factoryResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        factoryService.deleteFactory(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
