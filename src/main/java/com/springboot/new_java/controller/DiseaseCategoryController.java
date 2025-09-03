package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;

import com.springboot.new_java.data.dto.disease.DiseaseCategoryDto;

import com.springboot.new_java.data.entity.disease.DiseaseCategory;
import com.springboot.new_java.service.DiseaseCategoryService;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diseaseCategory")

public class DiseaseCategoryController extends AbstractSearchController<DiseaseCategory, DiseaseCategoryDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DiseaseCategoryController.class);

    private final DiseaseCategoryService diseaseCategoryService;


    public DiseaseCategoryController(DiseaseCategoryService diseaseCategoryService,
                                     RedisTemplate<String, Object> redisTemplate,
                                     ObjectMapper objectMapper) {
        super(diseaseCategoryService, redisTemplate,objectMapper);


        this.diseaseCategoryService = diseaseCategoryService;
    }

    @Override
    protected String getEntityPath() {
        return "diseaseCategorys";
    }

    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<DiseaseCategory>> createDiseaseCategory(@RequestBody DiseaseCategoryDto diseaseCategoryDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[diseaseCategoryDto] : {}", diseaseCategoryDto);

        DiseaseCategory result = diseaseCategoryService.insertDiseaseCategory(diseaseCategoryDto);

        LOGGER.info("[createDiseaseCategory] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<DiseaseCategory>> updateDiseaseCategory(@RequestBody DiseaseCategoryDto diseaseCategoryDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[diseaseCategoryDto] : {}", diseaseCategoryDto);

        DiseaseCategory result = diseaseCategoryService.updateDiseaseCategory(diseaseCategoryDto);

        LOGGER.info("[updateDiseaseCategory] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteDiseaseCategory(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        diseaseCategoryService.deleteDiseaseCategory(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
