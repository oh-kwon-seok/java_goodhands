package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.care.CareScheduleDto;
import com.springboot.new_java.data.entity.care.CareSchedule;
import com.springboot.new_java.service.CareScheduleBatchService;
import com.springboot.new_java.service.CareScheduleService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careSchedule")

public class CareScheduleController extends AbstractSearchController<CareSchedule, CareScheduleDto>{
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CareScheduleController.class);

    private final CareScheduleService careScheduleService;

    @Autowired
    private CareScheduleBatchService careScheduleBatchService;

    public CareScheduleController(CareScheduleService careScheduleService, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(careScheduleService,redisTemplate,objectMapper);
        this.careScheduleService = careScheduleService;


    }

    @Override
    protected String getEntityPath() {
        return "careSchedules";
    }


    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<CareSchedule>> createCareSchedule(@RequestBody CareScheduleDto careScheduleDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[careScheduleDto] : {}", careScheduleDto);

        CareSchedule result = careScheduleService.save(careScheduleDto);

        LOGGER.info("[createCareSchedule] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<CareSchedule>> updateCareSchedule(@RequestBody CareScheduleDto careScheduleDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[careScheduleDto] : {}", careScheduleDto);

        CareSchedule result = careScheduleService.update(careScheduleDto);

        LOGGER.info("[updateCareSchedule] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteCareSchedule(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        careScheduleService.delete(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }


    @PostMapping("/generate-next-week")
    public ResponseEntity<Map<String, Object>> generateNextWeekSchedule() {
        try {
            LOGGER.info("수동 다음주 케어 스케줄 생성 요청 시작");

            Map<String, Object> result = careScheduleBatchService.generateNextWeekScheduleManually();

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            LOGGER.error("수동 케어 스케줄 생성 실패: {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




}
