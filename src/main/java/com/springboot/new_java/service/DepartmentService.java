package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.repository.department.DepartmentRepository;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;


import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;


@Service
public class DepartmentService extends AbstractCacheableSearchService<Department, DepartmentDto> {

    private final DepartmentRepository departmentRepository;


    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DepartmentService.class);

    public DepartmentService(DepartmentRepository departmentRepository, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
        this.departmentRepository = departmentRepository;



    }
    @Override
    public String getEntityType() {
        return "Department";
    }

    @Override
    public List<Department> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return departmentRepository.findAll(searchDto);
    }

    @Override
    protected String[] getRelatedEntityTypes() {
        return new String[0];
    }


    @Transactional
    public Department save(DepartmentDto departmentDto) {
        try {
            // 1. Department 생성
            Department savedDepartment = performSave(departmentDto);
            LOGGER.info("Department 생성 완료: ID={}, Name={}", savedDepartment.getUid(), savedDepartment.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Department 생성 후 캐시 무효화 완료: {}", savedDepartment.getUid());

            return savedDepartment;

        } catch (Exception e) {
            LOGGER.error("Department 생성 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Department 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Department update(DepartmentDto departmentDto) {
        try {
            // 1. Department 수정
            Department updatedDepartment = performUpdate(departmentDto);

            if (updatedDepartment == null) {
                LOGGER.warn("수정할 Department를 찾을 수 없습니다: ID={}", departmentDto.getUid());
                return null;
            }

            LOGGER.info("Department 수정 완료: ID={}, Name={}", updatedDepartment.getUid(), updatedDepartment.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Department 수정 후 캐시 무효화 완료: {}", updatedDepartment.getUid());

            return updatedDepartment;

        } catch (Exception e) {
            LOGGER.error("Department 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Department 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }

    public Department performSave(DepartmentDto departmentDto) {
        Department department = new Department();

        department.setName(departmentDto.getName());
        department.setUsed(departmentDto.getUsed());
        department.setCreated(LocalDateTime.now());
        return departmentRepository.save(department);
    }
    public Department performUpdate(DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 부서입니다."));

        department.setName(departmentDto.getName());
        department.setUsed(departmentDto.getUsed());
        department.setUpdated(LocalDateTime.now());

        return departmentRepository.save(department);
    }


    public DepartmentDto convertToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setUid(department.getUid());
        dto.setName(department.getName());

        return dto;
    }





    /**
     * Department 삭제 후 캐시 자동 무효화
     */
    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<Department> existingDepartment = departmentRepository.findById(uid);
                if (existingDepartment.isPresent()) {
                    Department department = existingDepartment.get();
                    department.setUsed(false);
                    department.setDeleted(LocalDateTime.now());
                    departmentRepository.save(department);
                    deletedCount++;
                    LOGGER.debug("Department 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 Department를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("Department 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("Department 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("Department 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Department 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * Department 캐시 상태 조회
     */
    public CacheStatus getDepartmentCacheStatus() {
        return getCacheStatus();
    }

    /**
     * Excel 업로드를 통한 대량 Department 처리 후 캐시 무효화
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
//                    Optional<Department> existingDepartment = departmentRepository.findByNameAndEmail(name, email);
//
//                    if (existingDepartment.isPresent()) {
//                        // 수정
//                        Department department = existingDepartment.get();
//                        department.setPhone(phone);
//                        department.setUpdated(LocalDateTime.now());
//                        departmentRepository.save(department);
//                    } else {
//                        // 신규 생성
//                        Department department = Department.builder()
//                                .name(name)
//                                .email(email)
//                                .phone(phone)
//                                .created(LocalDateTime.now())
//                                .used(true)
//                                .build();
//                        departmentRepository.save(department);
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
//            return processedCount + "개의 Department 데이터가 처리되었습니다.";
//
//        } catch (Exception e) {
//            log.error("Excel 업로드 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
//            throw new RuntimeException("Excel 업로드에 실패했습니다: " + e.getMessage(), e);
//        }
//    }

    // ========================= 캐시 관리 메서드들 =========================






}