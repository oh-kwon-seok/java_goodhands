package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.care.CareHomeDto;
import com.springboot.new_java.data.entity.care.CareHome;
import com.springboot.new_java.service.CareHomeService;

import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careHome")

public class CareHomeController extends AbstractSearchController<CareHome, CareHomeDto>{
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CareHomeController.class);

    private final CareHomeService careHomeService;


    public CareHomeController(CareHomeService careHomeService, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(careHomeService,redisTemplate,objectMapper);
        this.careHomeService = careHomeService;


    }

    @Override
    protected String getEntityPath() {
        return "careHomes";
    }


    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<CareHome>> createCareHome(@RequestBody CareHomeDto careHomeDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[careHomeDto] : {}", careHomeDto);

        CareHome result = careHomeService.insertCareHome(careHomeDto);

        LOGGER.info("[createCareHome] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<CareHome>> updateCareHome(@RequestBody CareHomeDto careHomeDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[careHomeDto] : {}", careHomeDto);

        CareHome result = careHomeService.updateCareHome(careHomeDto);

        LOGGER.info("[updateCareHome] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteCareHome(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        careHomeService.deleteCareHome(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
