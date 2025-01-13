package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.estimate.EstimateDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.entity.Estimate;
import com.springboot.new_java.service.EstimateService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estimate")
@Controller
public class EstimateController {
    private final EstimateService estimateService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EstimateController.class);

    @Autowired
    public EstimateController(EstimateService estimateService){
        this.estimateService = estimateService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Estimate>> getEstimate(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Estimate> selectedEstimate = estimateService.getEstimate(commonInfoSearchDto);

        LOGGER.info("[getTotalEstimateSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedEstimate);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<Estimate>> getTotalEstimate(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Estimate> selectedTotalEstimate = estimateService.getTotalEstimate(commonSearchDto);

        LOGGER.info("[getTotalEstimate] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalEstimate);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createEstimate(@RequestBody EstimateDto estimateDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[estimateDto]  : {}", estimateDto);
        CommonResultDto estimateResultDto = estimateService.saveEstimate(estimateDto);
        LOGGER.info("[createEstimate] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  estimateResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateEstimate(@RequestBody EstimateDto estimateDto)
            throws Exception{
        CommonResultDto estimateResultDto = estimateService.updateEstimate(estimateDto);
        return estimateResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        estimateService.deleteEstimate(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
