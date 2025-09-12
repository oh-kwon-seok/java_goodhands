package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.springboot.new_java.data.dto.disease.DiseaseDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.User;

import com.springboot.new_java.data.entity.disease.Disease;

import com.springboot.new_java.data.entity.disease.DiseaseCategory;
import com.springboot.new_java.data.repository.disease.DiseaseRepository;
import com.springboot.new_java.data.repository.diseaseCategory.DiseaseCategoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiseaseService extends AbstractCacheableSearchService<Disease, DiseaseDto> {

    private final DiseaseRepository diseaseRepository;
    private final UserRepository userRepository;
    private final DiseaseCategoryRepository diseaseCategoryRepository;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DiseaseService.class);

    public DiseaseService(DiseaseRepository diseaseRepository, UserRepository userRepository, DiseaseCategoryRepository diseaseCategoryRepository, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
        this.diseaseRepository = diseaseRepository;
        this.userRepository = userRepository;
        this.diseaseCategoryRepository = diseaseCategoryRepository;


    }
    @Override
    public String getEntityType() {
        return "Disease";
    }

    @Override
    public List<Disease> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return diseaseRepository.findAll(searchDto);
    }

    @Override
    protected String[] getRelatedEntityTypes() {
        return new String[]{"CareHome", "User"};
    }


    @Transactional
    public Disease save(DiseaseDto diseaseDto) {
        try {
            // 1. Disease 생성
            Disease savedDisease = performSave(diseaseDto);
            LOGGER.info("Disease 생성 완료: ID={}, Name={}", savedDisease.getUid(), savedDisease.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Disease 생성 후 캐시 무효화 완료: {}", savedDisease.getUid());

            return savedDisease;

        } catch (Exception e) {
            LOGGER.error("Disease 생성 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Disease 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Disease update(DiseaseDto diseaseDto) {
        try {
            // 1. Disease 수정
            Disease updatedDisease = performUpdate(diseaseDto);

            if (updatedDisease == null) {
                LOGGER.warn("수정할 Disease를 찾을 수 없습니다: ID={}", diseaseDto.getUid());
                return null;
            }

            LOGGER.info("Disease 수정 완료: ID={}, Name={}", updatedDisease.getUid(), updatedDisease.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Disease 수정 후 캐시 무효화 완료: {}", updatedDisease.getUid());

            return updatedDisease;

        } catch (Exception e) {
            LOGGER.error("Disease 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Disease 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }



    public Disease performSave(DiseaseDto diseaseDto) {
        Disease disease = new Disease();
        String userId = diseaseDto.getUser_id();
        Long diseaseCategoryUid = diseaseDto.getDisease_category_uid();

        User user = userRepository.getById(userId);
        DiseaseCategory diseaseCategory = diseaseCategoryRepository.findByUid(diseaseCategoryUid);
        disease.setName(diseaseDto.getName());
        disease.setCode(diseaseDto.getCode());
        disease.setDiseaseCategory(diseaseCategory);
        disease.setUser(user);
        disease.setDisease_checklist(diseaseDto.getDisease_checklist());



        disease.setUsed(diseaseDto.getUsed());
        disease.setCreated(LocalDateTime.now());
        return diseaseRepository.save(disease);
    }
    public Disease performUpdate(DiseaseDto diseaseDto) {
        Disease disease = diseaseRepository.findById(diseaseDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String userId = diseaseDto.getUser_id();
        Long diseaseCategoryUid = diseaseDto.getDisease_category_uid();

        User user = userRepository.getById(userId);
        DiseaseCategory diseaseCategory = diseaseCategoryRepository.findByUid(diseaseCategoryUid);

        disease.setName(diseaseDto.getName());
        disease.setCode(diseaseDto.getCode());
        disease.setDiseaseCategory(diseaseCategory);
        disease.setUser(user);
        disease.setDisease_checklist(diseaseDto.getDisease_checklist());

        disease.setUsed(diseaseDto.getUsed());
        disease.setUpdated(LocalDateTime.now());

        return diseaseRepository.save(disease);
    }


    public DiseaseDto convertToDto(Disease disease) {
        DiseaseDto dto = new DiseaseDto();
        dto.setUid(disease.getUid());
        dto.setName(disease.getName());

        return dto;
    }
    /**
     * Disease 삭제 후 캐시 자동 무효화
     */
    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<Disease> existingDisease = diseaseRepository.findById(uid);
                if (existingDisease.isPresent()) {
                    Disease disease = existingDisease.get();
                    disease.setUsed(false);
                    disease.setDeleted(LocalDateTime.now());
                    diseaseRepository.save(disease);
                    deletedCount++;
                    LOGGER.debug("Disease 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 Disease를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("Disease 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("Disease 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("Disease 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Disease 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * Disease 캐시 상태 조회
     */
    public CacheStatus getDiseaseCacheStatus() {
        return getCacheStatus();
    }

    /**
     * Excel 업로드를 통한 대량 Disease 처리 후 캐시 무효화
     */
//    @Transactional
//    public String excelUploadWithCacheInvalidation(List<Map<String, Object>> requestList) {
//        try {
//            int processedCount = 0;
//
//            for (Map<String, Object> data : requestList) {
//                try {
//                    String name = String.valueOf(data.get("name"));
//                    String email = String.valueOf(data.get("email"));
//                    String phone = String.valueOf(data.get("phone"));
//
//                    // 기존 데이터 확인
//                    Optional<Disease> existingDisease = diseaseRepository.findByNameAndEmail(name, email);
//
//                    if (existingDisease.isPresent()) {
//                        // 수정
//                        Disease disease = existingDisease.get();
//                        disease.setPhone(phone);
//                        disease.setUpdated(LocalDateTime.now());
//                        diseaseRepository.save(disease);
//                    } else {
//                        // 신규 생성
//                        Disease disease = Disease.builder()
//                                .name(name)
//                                .email(email)
//                                .phone(phone)
//                                .created(LocalDateTime.now())
//                                .used(true)
//                                .build();
//                        diseaseRepository.save(disease);
//                    }
//                    processedCount++;
//
//                } catch (Exception e) {
//                    log.error("Excel 데이터 처리 중 오류: {}", e.getMessage(), e);
//                    // 개별 데이터 오류는 로그만 남기고 계속 진행
//                }
//            }
//
//            log.info("Excel 업로드 처리 완료: {}개 처리", processedCount);
//
//            // 캐시 무효화
//            if (processedCount > 0) {
//                invalidateCachesAfterDataChange();
//                log.info("Excel 업로드 후 캐시 무효화 완료");
//            }
//
//            return processedCount + "개의 Disease 데이터가 처리되었습니다.";
//
//        } catch (Exception e) {
//            log.error("Excel 업로드 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
//            throw new RuntimeException("Excel 업로드에 실패했습니다: " + e.getMessage(), e);
//        }
//    }

    // ========================= 캐시 관리 메서드들 =========================






}