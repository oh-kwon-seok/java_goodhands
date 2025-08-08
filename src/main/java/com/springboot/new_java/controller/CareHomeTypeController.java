package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.care.CareHomeTypeDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.service.CareHomeTypeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careHomeType")

public class CareHomeTypeController {
    private final CareHomeTypeService careHomeTypeService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CareHomeTypeController.class);

    @Autowired
    public CareHomeTypeController(CareHomeTypeService careHomeTypeService) {
        this.careHomeTypeService = careHomeTypeService;
    }
    @GetMapping("/select")
    public ResponseEntity<CommonApiResponse<List<CareHomeType>>> getTotalCareHomeType(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) {
        long start = System.currentTimeMillis();
        List<CareHomeType> careHomeTypes = careHomeTypeService.getTotalCareHomeType(commonInfoSearchDto);
        LOGGER.info("[getTotalCareHomeType] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(careHomeTypes));
    }
    @GetMapping("/info_select")
    public ResponseEntity<CommonApiResponse<List<CareHomeType>>> getCareHomeType(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) {
        long start = System.currentTimeMillis();
        List<CareHomeType> careHomeTypes = careHomeTypeService.getCareHomeType(commonInfoSearchDto);
        LOGGER.info("[getCareHomeType] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(careHomeTypes));
    }

    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<CareHomeType>> createCareHomeType(@RequestBody CareHomeTypeDto careHomeTypeDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[careHomeTypeDto] : {}", careHomeTypeDto);

        CareHomeType result = careHomeTypeService.insertCareHomeType(careHomeTypeDto);

        LOGGER.info("[createCareHomeType] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<CareHomeType>> updateCareHomeType(@RequestBody CareHomeTypeDto careHomeTypeDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[careHomeTypeDto] : {}", careHomeTypeDto);

        CareHomeType result = careHomeTypeService.updateCareHomeType(careHomeTypeDto);

        LOGGER.info("[updateCareHomeType] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteCareHomeType(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        careHomeTypeService.deleteCareHomeType(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
