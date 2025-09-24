package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.springboot.new_java.data.dto.employment.EmploymentDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.Employment;
import com.springboot.new_java.data.repository.employment.EmploymentRepository;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;


import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;


@Service
public class EmploymentService extends AbstractCacheableSearchService<Employment, EmploymentDto> {

    private final EmploymentRepository employmentRepository;


    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EmploymentService.class);

    public EmploymentService(EmploymentRepository employmentRepository, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
        this.employmentRepository = employmentRepository;



    }
    @Override
    public String getEntityType() {
        return "Employment";
    }

    @Override
    public List<Employment> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return employmentRepository.findAll(searchDto);
    }

    @Override
    protected String[] getRelatedEntityTypes() {
        return new String[0];
    }


    @Transactional
    public Employment save(EmploymentDto employmentDto) {
        try {
            // 1. Employment 생성
            Employment savedEmployment = performSave(employmentDto);
            LOGGER.info("Employment 생성 완료: ID={}, Name={}", savedEmployment.getUid(), savedEmployment.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Employment 생성 후 캐시 무효화 완료: {}", savedEmployment.getUid());

            return savedEmployment;

        } catch (Exception e) {
            LOGGER.error("Employment 생성 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Employment 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Employment update(EmploymentDto employmentDto) {
        try {
            // 1. Employment 수정
            Employment updatedEmployment = performUpdate(employmentDto);

            if (updatedEmployment == null) {
                LOGGER.warn("수정할 Employment를 찾을 수 없습니다: ID={}", employmentDto.getUid());
                return null;
            }

            LOGGER.info("Employment 수정 완료: ID={}, Name={}", updatedEmployment.getUid(), updatedEmployment.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Employment 수정 후 캐시 무효화 완료: {}", updatedEmployment.getUid());

            return updatedEmployment;

        } catch (Exception e) {
            LOGGER.error("Employment 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Employment 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }



    public Employment performSave(EmploymentDto employmentDto) {
        Employment employment = new Employment();

        employment.setName(employmentDto.getName());
        employment.setName2(employmentDto.getName2());



        employment.setUsed(employmentDto.getUsed());
        employment.setCreated(LocalDateTime.now());
        return employmentRepository.save(employment);
    }
    public Employment performUpdate(EmploymentDto employmentDto) {
        Employment employment = employmentRepository.findById(employmentDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 직급입니다."));

        employment.setName(employmentDto.getName());
        employment.setName2(employmentDto.getName2());

        employment.setUsed(employmentDto.getUsed());
        employment.setUpdated(LocalDateTime.now());

        return employmentRepository.save(employment);
    }


    public EmploymentDto convertToDto(Employment employment) {
        EmploymentDto dto = new EmploymentDto();
        dto.setUid(employment.getUid());
        dto.setName(employment.getName());
        dto.setName2(employment.getName2());

        return dto;
    }





    /**
     * Employment 삭제 후 캐시 자동 무효화
     */
    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<Employment> existingEmployment = employmentRepository.findById(uid);
                if (existingEmployment.isPresent()) {
                    Employment employment = existingEmployment.get();
                    employment.setUsed(false);
                    employment.setDeleted(LocalDateTime.now());
                    employmentRepository.save(employment);
                    deletedCount++;
                    LOGGER.debug("Employment 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 Employment를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("Employment 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("Employment 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("Employment 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Employment 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * Employment 캐시 상태 조회
     */
    public CacheStatus getEmploymentCacheStatus() {
        return getCacheStatus();
    }

    /**
     * Excel 업로드를 통한 대량 Employment 처리 후 캐시 무효화
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
//                    Optional<Employment> existingEmployment = employmentRepository.findByNameAndEmail(name, email);
//
//                    if (existingEmployment.isPresent()) {
//                        // 수정
//                        Employment employment = existingEmployment.get();
//                        employment.setPhone(phone);
//                        employment.setUpdated(LocalDateTime.now());
//                        employmentRepository.save(employment);
//                    } else {
//                        // 신규 생성
//                        Employment employment = Employment.builder()
//                                .name(name)
//                                .email(email)
//                                .phone(phone)
//                                .created(LocalDateTime.now())
//                                .used(true)
//                                .build();
//                        employmentRepository.save(employment);
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
//            return processedCount + "개의 Employment 데이터가 처리되었습니다.";
//
//        } catch (Exception e) {
//            log.error("Excel 업로드 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
//            throw new RuntimeException("Excel 업로드에 실패했습니다: " + e.getMessage(), e);
//        }
//    }

    // ========================= 캐시 관리 메서드들 =========================






}