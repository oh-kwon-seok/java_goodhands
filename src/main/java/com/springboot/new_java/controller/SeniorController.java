package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.senior.SeniorDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.service.SeniorService;
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
@RequestMapping("/senior")

public class SeniorController extends AbstractSearchController<Senior, SeniorDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SeniorController.class);
    private final SeniorService seniorService;


    public SeniorController(SeniorService seniorService,
    RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(seniorService,redisTemplate,objectMapper);
        this.seniorService = seniorService;
    }

    @Override
    protected String getEntityPath() {
        return "seniors";
    }
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Senior>> createSenior(@RequestBody SeniorDto seniorDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[seniorDto] : {}", seniorDto);

        Senior result = seniorService.insertSenior(seniorDto);

        LOGGER.info("[createSenior] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Senior>> updateSenior(@RequestBody SeniorDto seniorDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[seniorDto] : {}", seniorDto);

        Senior result = seniorService.updateSenior(seniorDto);

        LOGGER.info("[updateSenior] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteSenior(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        seniorService.deleteSenior(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
