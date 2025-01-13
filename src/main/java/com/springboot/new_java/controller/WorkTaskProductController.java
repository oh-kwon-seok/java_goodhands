package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.new_java.data.entity.WorkTaskProduct;
import com.springboot.new_java.service.WorkTaskProductService;
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
@RequestMapping("/work_task_product")
@Controller
public class WorkTaskProductController {
    private final WorkTaskProductService workTaskProductService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskProductController.class);

    @Autowired
    public WorkTaskProductController(WorkTaskProductService workTaskProductService){
        this.workTaskProductService = workTaskProductService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<WorkTaskProduct>> getWorkTaskProduct(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkTaskProduct> selectedWorkTaskProduct = workTaskProductService.getWorkTaskProduct(commonInfoSearchDto);

        LOGGER.info("[getTotalWorkTaskProductSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedWorkTaskProduct);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<WorkTaskProduct>> getTotalWorkTaskProduct(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkTaskProduct> selectedTotalWorkTaskProduct = workTaskProductService.getTotalWorkTaskProduct(commonSearchDto);

        LOGGER.info("[getTotalWorkTaskProduct] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalWorkTaskProduct);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<WorkTaskProduct>> getWorkTaskUidSelect(@ModelAttribute WorkTaskProductSearchDto workTaskProductSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkTaskProduct> selectWorkTaskUidSelect = workTaskProductService.getWorkTaskUidSelect(workTaskProductSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectWorkTaskUidSelect);

    }







}
