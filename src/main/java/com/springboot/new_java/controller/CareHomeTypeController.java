package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.care.CareHomeDto;
import com.springboot.new_java.data.dto.care.CareHomeTypeDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.service.CareHomeTypeService;
import com.springboot.new_java.service.DepartmentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careHomeType")

public class CareHomeTypeController extends AbstractSearchController<CareHomeType, CareHomeTypeDto> {
    private final CareHomeTypeService careHomeTypeService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CareHomeTypeController.class);

    public CareHomeTypeController(CareHomeTypeService careHomeTypeService,
                                RedisTemplate<String, Object> redisTemplate,
                                ObjectMapper objectMapper) {
        super(careHomeTypeService, redisTemplate, objectMapper); // 이 줄이 필수!

        this.careHomeTypeService = careHomeTypeService;

    }
    @Override
    protected String getEntityPath() {
        return "careHomeTypes";
    }

    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<CareHomeType>> createCareHomeType(@RequestBody CareHomeTypeDto careHomeTypeDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[careHomeTypeDto] : {}", careHomeTypeDto);

        CareHomeType result = careHomeTypeService.save(careHomeTypeDto);

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

        CareHomeType result = careHomeTypeService.update(careHomeTypeDto);

        LOGGER.info("[updateCareHomeType] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteCareHomeType(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        careHomeTypeService.delete(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
