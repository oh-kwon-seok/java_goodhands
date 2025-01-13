package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.new_java.data.entity.WorkTaskPacking;
import com.springboot.new_java.service.WorkTaskPackingService;
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
@RequestMapping("/work_task_packing")
@Controller
public class WorkTaskPackingController {
    private final WorkTaskPackingService workTaskPackingService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskPackingController.class);

    @Autowired
    public WorkTaskPackingController(WorkTaskPackingService workTaskPackingService){
        this.workTaskPackingService = workTaskPackingService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<WorkTaskPacking>> getWorkTaskPacking(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkTaskPacking> selectedWorkTaskPacking = workTaskPackingService.getWorkTaskPacking(commonInfoSearchDto);

        LOGGER.info("[getTotalWorkTaskPackingSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedWorkTaskPacking);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<WorkTaskPacking>> getTotalWorkTaskPacking(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkTaskPacking> selectedTotalWorkTaskPacking = workTaskPackingService.getTotalWorkTaskPacking(commonSearchDto);

        LOGGER.info("[getTotalWorkTaskPacking] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalWorkTaskPacking);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<WorkTaskPacking>> getWorkTaskUidSelect(@ModelAttribute WorkTaskPackingSearchDto workTaskPackingSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkTaskPacking> selectWorkTaskUidSelect = workTaskPackingService.getWorkTaskUidSelect(workTaskPackingSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectWorkTaskUidSelect);

    }







}
