package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.senior.SeniorDto;
import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.service.SeniorService;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/senior")
public class SeniorController extends AbstractSearchController<Senior, SeniorDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SeniorController.class);
    private final SeniorService seniorService;

    public SeniorController(SeniorService seniorService,
                            RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(seniorService, redisTemplate, objectMapper);
        this.seniorService = seniorService;
    }

    @Override
    protected String getEntityPath() {
        return "seniors";
    }

    /**
     * Senior 생성 후 캐시 자동 무효화
     */
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Senior>> createSenior(@RequestBody SeniorDto seniorDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[seniorDto] : {}", seniorDto);

        try {
            // Service에서 저장과 캐시 무효화를 한번에 처리
            Senior result = seniorService.save(seniorDto);

            LOGGER.info("[createSenior] response time: {}ms", System.currentTimeMillis() - start);

            if (result == null) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.error("Senior 생성에 실패했습니다.")
                );
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    CommonApiResponse.success(result, "Senior 등록이 성공적으로 완료되었습니다.")
            );


        } catch (Exception e) {
            LOGGER.error("Senior 생성 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("Senior 생성 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }

    /**
     * Senior 수정 후 캐시 자동 무효화
     */
    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Senior>> updateSenior(@RequestBody SeniorDto seniorDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[seniorDto] : {}", seniorDto);

        try {
            // Service에서 수정과 캐시 무효화를 한번에 처리
            Senior result = seniorService.update(seniorDto);

            LOGGER.info("[updateSenior] response time: {}ms", System.currentTimeMillis() - start);

            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        CommonApiResponse.error("해당 Senior를 찾을 수 없습니다.")
                );
            }

            return ResponseEntity.ok(
                    CommonApiResponse.success(result, "Senior 수정이 성공적으로 완료되었습니다.")
            );

        } catch (Exception e) {
            LOGGER.error("Senior 수정 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("Senior 수정 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }

    /**
     * Senior 삭제 후 캐시 자동 무효화
     */
    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteSenior(@RequestBody Map<String, List<Long>> requestMap) {
        long start = System.currentTimeMillis();
        List<Long> uids = requestMap.get("uid");
        LOGGER.info("[deleteSenior] uid list: {}", uids);

        try {
            if (uids == null || uids.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.error("삭제할 Senior ID가 제공되지 않았습니다.")
                );
            }

            // Service에서 삭제와 캐시 무효화를 한번에 처리
            int deletedCount = seniorService.delete(uids);

            LOGGER.info("[deleteSenior] response time: {}ms", System.currentTimeMillis() - start);

            if (deletedCount == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        CommonApiResponse.error("삭제할 Senior를 찾을 수 없습니다.")
                );
            }

            return ResponseEntity.ok(
                    CommonApiResponse.success(
                            deletedCount + "개의 Senior가 정상적으로 삭제되었습니다.",
                            "Senior 삭제 완료"
                    )
            );

        } catch (Exception e) {
            LOGGER.error("Senior 삭제 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("Senior 삭제 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }

    /**
     * 캐시 상태 조회 (관리자용)
     */
    @PostMapping(value = "/cache/status", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Object>> getCacheStatus() {
        try {
            var cacheStatus = seniorService.getSeniorCacheStatus();
            return ResponseEntity.ok(
                    CommonApiResponse.success(cacheStatus, "캐시 상태 조회 완료")
            );
        } catch (Exception e) {
            LOGGER.error("캐시 상태 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("캐시 상태 조회 중 오류가 발생했습니다.")
            );
        }
    }

    /**
     * 수동 캐시 무효화 (관리자용)
     */
    @PostMapping(value = "/cache/invalidate", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> invalidateCache() {
        try {
            seniorService.invalidateAllCaches();
            return ResponseEntity.ok(
                    CommonApiResponse.success("캐시 무효화가 완료되었습니다.", "캐시 무효화 완료")
            );
        } catch (Exception e) {
            LOGGER.error("캐시 무효화 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("캐시 무효화 중 오류가 발생했습니다.")
            );
        }
    }



    /**
     * Excel 업로드를 통한 대량 Senior 처리 후 캐시 무효화
     */
//    @PostMapping(value = "/excel/upload", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<CommonApiResponse<String>> excelUploadSenior(
//            @RequestBody List<Map<String, Object>> requestList) {
//        long start = System.currentTimeMillis();
//        LOGGER.info("[excelUploadSenior] 업로드 데이터 수: {}", requestList.size());
//
//        try {
//            if (requestList == null || requestList.isEmpty()) {
//                return ResponseEntity.badRequest().body(
//                        CommonApiResponse.error("업로드할 데이터가 없습니다.")
//                );
//            }
//
//            // Service에서 대량 처리와 캐시 무효화를 한번에 처리
//            String result = seniorService.excelUploadWithCacheInvalidation(requestList);
//
//            LOGGER.info("[excelUploadSenior] response time: {}ms", System.currentTimeMillis() - start);
//
//            return ResponseEntity.ok(
//                    CommonApiResponse.success(result, "Excel 업로드가 성공적으로 완료되었습니다.")
//            );
//
//        } catch (Exception e) {
//            LOGGER.error("Excel 업로드 중 오류 발생: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                    CommonApiResponse.error("Excel 업로드 중 오류가 발생했습니다: " + e.getMessage())
//            );
//        }
//    }
}