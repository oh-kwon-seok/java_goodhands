package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.controller.SeniorController;
import com.springboot.new_java.data.dto.SignUpResultDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.senior.SeniorDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.user.UserDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.User;

import com.springboot.new_java.data.entity.care.CareHome;
import com.springboot.new_java.data.entity.senior.Senior;

import com.springboot.new_java.data.repository.careHome.CareHomeRepository;
import com.springboot.new_java.data.repository.senior.SeniorRepository;

import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeniorService extends AbstractCacheableSearchService<Senior, SeniorDto> {

    private final SeniorRepository seniorRepository;
    private final UserRepository userRepository;
    private final CareHomeRepository careHomeRepository;

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SeniorService.class);

    public SeniorService(SeniorRepository seniorRepository, UserRepository userRepository, CareHomeRepository careHomeRepository, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
        this.seniorRepository = seniorRepository;
        this.userRepository = userRepository;
        this.careHomeRepository = careHomeRepository;


    }
    @Override
    public String getEntityType() {
        return "Senior";
    }

    @Override
    public List<Senior> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return seniorRepository.findAll(searchDto);
    }

    @Override
    protected String[] getRelatedEntityTypes() {
        return new String[]{"CareHome", "User"};
    }


    @Transactional
    public Senior save(SeniorDto seniorDto) {
        Senior savedSenior = null;
        try {
            LOGGER.info("Senior 생성 시작: {}", seniorDto.getName());

            // 1. Senior 생성
            savedSenior = performSave(seniorDto);
            LOGGER.info("Senior 생성 완료: ID={}, Name={}", savedSenior.getUid(), savedSenior.getName());

            // 2. 캐시 무효화
            LOGGER.info("캐시 무효화 시작");
            invalidateCachesAfterDataChange();
            LOGGER.info("Senior 생성 후 캐시 무효화 완료: {}", savedSenior.getUid());

            return savedSenior;

        } catch (Exception e) {
            LOGGER.error("Senior 생성 중 오류 발생: {}", e.getMessage(), e);
            // 데이터는 저장되었지만 캐시 무효화 실패한 경우라도 캐시 무효화 시도
            if (savedSenior != null) {
                try {
                    LOGGER.info("예외 발생했지만 캐시 무효화 재시도");
                    invalidateCachesAfterDataChange();
                } catch (Exception cacheEx) {
                    LOGGER.error("캐시 무효화 재시도도 실패: {}", cacheEx.getMessage());
                }
            }
            throw new RuntimeException("Senior 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Senior update(SeniorDto seniorDto) {
        try {
            // 1. Senior 수정
            Senior updatedSenior = performUpdate(seniorDto);

            if (updatedSenior == null) {
                LOGGER.warn("수정할 Senior를 찾을 수 없습니다: ID={}", seniorDto.getUid());
                return null;
            }

            LOGGER.info("Senior 수정 완료: ID={}, Name={}", updatedSenior.getUid(), updatedSenior.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Senior 수정 후 캐시 무효화 완료: {}", updatedSenior.getUid());

            return updatedSenior;

        } catch (Exception e) {
            LOGGER.error("Senior 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Senior 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }



    public Senior performSave(SeniorDto seniorDto) {
        Senior senior = new Senior();
        String userId = seniorDto.getUser_id();
        String caregiverId = seniorDto.getCaregiver_id();

        User user = userRepository.getById(userId);
        User caregiver = userRepository.getById(caregiverId);
        CareHome careHome = careHomeRepository.findByUid(seniorDto.getCare_home_uid());

        senior.setName(seniorDto.getName());
        senior.setBirth_date(seniorDto.getBirth_date());
        senior.setUser(user);
        senior.setCaregiver(caregiver);
        senior.setCareHome(careHome);
        senior.setUse_care_schedule(seniorDto.getUse_care_schedule());
        senior.setCare_schedule_protocol(seniorDto.getCare_schedule_protocol());

        senior.setUsed(seniorDto.getUsed());
        senior.setCreated(LocalDateTime.now());
        return seniorRepository.save(senior);
    }
    public Senior performUpdate(SeniorDto seniorDto) {
        Senior senior = seniorRepository.findById(seniorDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String userId = seniorDto.getUser_id();
        String caregiverId = seniorDto.getCaregiver_id();
        CareHome careHome = careHomeRepository.findByUid(seniorDto.getCare_home_uid());
        User user = userRepository.getById(userId);
        User caregiver = userRepository.getById(caregiverId);
        senior.setName(seniorDto.getName());
        senior.setBirth_date(seniorDto.getName());
        senior.setUser(user);
        senior.setCaregiver(caregiver);
        senior.setCareHome(careHome);
        senior.setUse_care_schedule(seniorDto.getUse_care_schedule());
        senior.setCare_schedule_protocol(seniorDto.getCare_schedule_protocol());

        senior.setUsed(seniorDto.getUsed());
        senior.setUpdated(LocalDateTime.now());

        return seniorRepository.save(senior);
    }


    public SeniorDto convertToDto(Senior senior) {
        SeniorDto dto = new SeniorDto();
        dto.setUid(senior.getUid());
        dto.setName(senior.getName());
        dto.setBirth_date(senior.getBirth_date());

        // 연관 엔티티 정보 안전하게 추가
        if (senior.getUser() != null) {
            dto.setUser_id(senior.getUser().getId());
        }
        if (senior.getCaregiver() != null) {
            dto.setCaregiver_id(senior.getCaregiver().getId());
        }
        if (senior.getCareHome() != null) {
            dto.setCare_home_uid(senior.getCareHome().getUid());
        }

        dto.setUse_care_schedule(senior.getUse_care_schedule());
        dto.setCare_schedule_protocol(senior.getCare_schedule_protocol());
        dto.setUsed(senior.getUsed());
        dto.setCreated(senior.getCreated());

        return dto;
    }





    /**
     * Senior 삭제 후 캐시 자동 무효화
     */
    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<Senior> existingSenior = seniorRepository.findById(uid);
                if (existingSenior.isPresent()) {
                    Senior senior = existingSenior.get();
                    senior.setUsed(false);
                    senior.setDeleted(LocalDateTime.now());
                    seniorRepository.save(senior);
                    deletedCount++;
                    LOGGER.debug("Senior 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 Senior를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("Senior 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("Senior 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("Senior 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Senior 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * Senior 캐시 상태 조회
     */
    public CacheStatus getSeniorCacheStatus() {
        return getCacheStatus();
    }

    /**
     * Excel 업로드를 통한 대량 Senior 처리 후 캐시 무효화
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
//                    Optional<Senior> existingSenior = seniorRepository.findByNameAndEmail(name, email);
//
//                    if (existingSenior.isPresent()) {
//                        // 수정
//                        Senior senior = existingSenior.get();
//                        senior.setPhone(phone);
//                        senior.setUpdated(LocalDateTime.now());
//                        seniorRepository.save(senior);
//                    } else {
//                        // 신규 생성
//                        Senior senior = Senior.builder()
//                                .name(name)
//                                .email(email)
//                                .phone(phone)
//                                .created(LocalDateTime.now())
//                                .used(true)
//                                .build();
//                        seniorRepository.save(senior);
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
//            return processedCount + "개의 Senior 데이터가 처리되었습니다.";
//
//        } catch (Exception e) {
//            log.error("Excel 업로드 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
//            throw new RuntimeException("Excel 업로드에 실패했습니다: " + e.getMessage(), e);
//        }
//    }

    // ========================= 캐시 관리 메서드들 =========================






}