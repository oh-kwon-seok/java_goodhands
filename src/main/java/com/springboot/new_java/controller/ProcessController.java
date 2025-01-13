package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.process.ProcessDto;
import com.springboot.new_java.data.entity.Process;
import com.springboot.new_java.service.ProcessService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/process")
@Controller
public class ProcessController {
    private final ProcessService processService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    public ProcessController(ProcessService processService){
        this.processService = processService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Process>> getProcess(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Process> selectedProcess = processService.getProcess(commonInfoSearchDto);

        LOGGER.info("[getTotalProcessSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedProcess);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<Process>> getTotalProcess(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Process> selectedTotalProcess = processService.getTotalProcess(commonInfoSearchDto);

        LOGGER.info("[getTotalProcess] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalProcess);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createProcess(@RequestBody ProcessDto processDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[processDto]  : {}", processDto);
        CommonResultDto processResultDto = processService.saveProcess(processDto);
        LOGGER.info("[createProcess] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  processResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateProcess(@RequestBody ProcessDto processDto)
            throws Exception{


        CommonResultDto processResultDto = processService.updateProcess(processDto);
        return processResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        processService.deleteProcess(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadProcess(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        processService.excelUploadProcess(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 업로드되었습니다.");
    }

}
