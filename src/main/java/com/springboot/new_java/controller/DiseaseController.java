package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.disease.DiseaseDto;


import com.springboot.new_java.data.entity.disease.Disease;
import com.springboot.new_java.service.DiseaseService;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/disease")
public class DiseaseController extends AbstractSearchController<Disease, DiseaseDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DiseaseController.class);


    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService,
                             RedisTemplate<String, Object> redisTemplate,
                             ObjectMapper objectMapper) {
        super(diseaseService,redisTemplate,objectMapper);
        this.diseaseService = diseaseService;
    }
    @Override
    protected String getEntityPath() {
        return "diseases";
    }


    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Disease>> createDisease(@RequestBody DiseaseDto diseaseDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[diseaseDto] : {}", diseaseDto);

        Disease result = diseaseService.insertDisease(diseaseDto);

        LOGGER.info("[createDisease] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Disease>> updateDisease(@RequestBody DiseaseDto diseaseDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[diseaseDto] : {}", diseaseDto);

        Disease result = diseaseService.updateDisease(diseaseDto);

        LOGGER.info("[updateDisease] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteDisease(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        diseaseService.deleteDisease(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
