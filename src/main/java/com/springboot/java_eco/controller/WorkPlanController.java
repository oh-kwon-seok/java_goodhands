package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workPlan.WorkPlanDto;
import com.springboot.java_eco.data.entity.WorkPlan;
import com.springboot.java_eco.service.WorkPlanService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work_plan")
@Controller
public class WorkPlanController {
    private final WorkPlanService workPlanService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkPlanController.class);

    @Autowired
    public WorkPlanController(WorkPlanService workPlanService){
        this.workPlanService = workPlanService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<WorkPlan>> getWorkPlan(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkPlan> selectedWorkPlan = workPlanService.getWorkPlan(commonInfoSearchDto);

        LOGGER.info("[getTotalWorkPlanSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedWorkPlan);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<WorkPlan>> getTotalWorkPlan(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<WorkPlan> selectedTotalWorkPlan = workPlanService.getTotalWorkPlan(commonSearchDto);

        LOGGER.info("[getTotalWorkPlan] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalWorkPlan);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createWorkPlan(@RequestBody WorkPlanDto workPlanDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[workPlanDto]  : {}", workPlanDto);
        CommonResultDto workPlanResultDto = workPlanService.saveWorkPlan(workPlanDto);
        LOGGER.info("[createWorkPlan] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  workPlanResultDto;

    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateWorkPlan(@RequestBody WorkPlanDto workPlanDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[workPlanDto]  : {}", workPlanDto);
        CommonResultDto workPlanResultDto = workPlanService.updateWorkPlan(workPlanDto);
        LOGGER.info("[createWorkPlan] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  workPlanResultDto;

    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteWorkPlan(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        workPlanService.deleteWorkPlan(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
