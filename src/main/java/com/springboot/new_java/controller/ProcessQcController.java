package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.new_java.data.entity.ProcessQc;
import com.springboot.new_java.service.ProcessQcService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/process_qc")
@Controller
public class ProcessQcController {
    private final ProcessQcService processQcService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProcessQcController.class);

    @Autowired
    public ProcessQcController(ProcessQcService processQcService){
        this.processQcService = processQcService;
    }

    @GetMapping(value= "/select") // 매입처별/품목별 매입,  품목별/상품별 매입단가 조회
    public ResponseEntity<List<ProcessQc>> getTotalProcessQc(@ModelAttribute ProcessQcSearchDto processQcSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();
        List<ProcessQc> selectedTotalProcessQc = processQcService.getTotalProcessQc(processQcSearchDto);

        LOGGER.info("[getTotalUserOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalProcessQc);
    }



    @GetMapping(value= "/info_select")
    public ResponseEntity<List<ProcessQc>> getProcessQc(@ModelAttribute ProcessQcSearchDto processQcSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<ProcessQc> selectedTotalProcessQc = processQcService.getProcessQc(processQcSearchDto);

        LOGGER.info("[getTotalProcessQc] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalProcessQc);

    }
    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadProcessQc(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        processQcService.excelUploadProcessQc(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 업로드되었습니다.");
    }



}
