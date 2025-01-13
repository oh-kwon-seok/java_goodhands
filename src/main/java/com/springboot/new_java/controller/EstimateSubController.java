package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.estimateSub.EstimateSubSearchDto;
import com.springboot.new_java.data.entity.EstimateSub;
import com.springboot.new_java.service.EstimateSubService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estimate_sub")
@Controller
public class EstimateSubController {
    private final EstimateSubService estimateSubService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EstimateSubController.class);

    @Autowired
    public EstimateSubController(EstimateSubService estimateSubService){
        this.estimateSubService = estimateSubService;
    }

    @GetMapping(value= "/select") // 매입처별/품목별 매입,  품목별/상품별 매입단가 조회
    public ResponseEntity<List<EstimateSub>> getTotalEstimateSub(@ModelAttribute EstimateSubSearchDto estimateSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();
        List<EstimateSub> selectedTotalEstimateSub = estimateSubService.getTotalEstimateSub(estimateSubSearchDto);

        LOGGER.info("[getTotalUserOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalEstimateSub);
    }



    @GetMapping(value= "/info_select")
    public ResponseEntity<List<EstimateSub>> getEstimateSub(@ModelAttribute EstimateSubSearchDto estimateSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<EstimateSub> selectedTotalEstimateSub = estimateSubService.getEstimateSub(estimateSubSearchDto);

        LOGGER.info("[getTotalEstimateSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalEstimateSub);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<EstimateSub>> getEstimateUidSelect(@ModelAttribute EstimateSubSearchDto estimateSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<EstimateSub> selectEstimateUidSelect = estimateSubService.getEstimateUidSelect(estimateSubSearchDto);

        LOGGER.info("[getTotalEstimateSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectEstimateUidSelect);

    }


}
