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
                                RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(diseaseService, redisTemplate, objectMapper);
        this.diseaseService = diseaseService;
    }

    @Override
    protected String getEntityPath() {
        return "diseases";
    }

    /**
     * Disease 생성 후 캐시 자동 무효화
     */
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Disease>> createDisease(@RequestBody DiseaseDto diseaseDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[diseaseDto] : {}", diseaseDto);

        try {
            // Service에서 저장과 캐시 무효화를 한번에 처리
            Disease result = diseaseService.save(diseaseDto);

            LOGGER.info("[createDisease] response time: {}ms", System.currentTimeMillis() - start);

            if (result == null) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.error("Disease 생성에 실패했습니다.")
                );
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    CommonApiResponse.success(result, "Disease 등록이 성공적으로 완료되었습니다.")
            );

        } catch (Exception e) {
            LOGGER.error("Disease 생성 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("Disease 생성 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }

    /**
     * Disease 수정 후 캐시 자동 무효화
     */
    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Disease>> updateDisease(@RequestBody DiseaseDto diseaseDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[diseaseDto] : {}", diseaseDto);

        try {
            // Service에서 수정과 캐시 무효화를 한번에 처리
            Disease result = diseaseService.update(diseaseDto);

            LOGGER.info("[updateDisease] response time: {}ms", System.currentTimeMillis() - start);

            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        CommonApiResponse.error("해당 Disease를 찾을 수 없습니다.")
                );
            }

            return ResponseEntity.ok(
                    CommonApiResponse.success(result, "Disease 수정이 성공적으로 완료되었습니다.")
            );

        } catch (Exception e) {
            LOGGER.error("Disease 수정 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("Disease 수정 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }

    /**
     * Disease 삭제 후 캐시 자동 무효화
     */
    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteDisease(@RequestBody Map<String, List<Long>> requestMap) {
        long start = System.currentTimeMillis();
        List<Long> uids = requestMap.get("uid");
        LOGGER.info("[deleteDisease] uid list: {}", uids);

        try {
            if (uids == null || uids.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.error("삭제할 Disease ID가 제공되지 않았습니다.")
                );
            }

            // Service에서 삭제와 캐시 무효화를 한번에 처리
            int deletedCount = diseaseService.delete(uids);

            LOGGER.info("[deleteDisease] response time: {}ms", System.currentTimeMillis() - start);

            if (deletedCount == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        CommonApiResponse.error("삭제할 Disease를 찾을 수 없습니다.")
                );
            }

            return ResponseEntity.ok(
                    CommonApiResponse.success(
                            deletedCount + "개의 Disease가 정상적으로 삭제되었습니다.",
                            "Disease 삭제 완료"
                    )
            );

        } catch (Exception e) {
            LOGGER.error("Disease 삭제 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.error("Disease 삭제 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }

    /**
     * 캐시 상태 조회 (관리자용)
     */
    @PostMapping(value = "/cache/status", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Object>> getCacheStatus() {
        try {
            var cacheStatus = diseaseService.getDiseaseCacheStatus();
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
            diseaseService.invalidateAllCaches();
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
     * Excel 업로드를 통한 대량 Disease 처리 후 캐시 무효화
     */
//    @PostMapping(value = "/excel/upload", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<CommonApiResponse<String>> excelUploadDisease(
//            @RequestBody List<Map<String, Object>> requestList) {
//        long start = System.currentTimeMillis();
//        LOGGER.info("[excelUploadDisease] 업로드 데이터 수: {}", requestList.size());
//
//        try {
//            if (requestList == null || requestList.isEmpty()) {
//                return ResponseEntity.badRequest().body(
//                        CommonApiResponse.error("업로드할 데이터가 없습니다.")
//                );
//            }
//
//            // Service에서 대량 처리와 캐시 무효화를 한번에 처리
//            String result = diseaseService.excelUploadWithCacheInvalidation(requestList);
//
//            LOGGER.info("[excelUploadDisease] response time: {}ms", System.currentTimeMillis() - start);
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