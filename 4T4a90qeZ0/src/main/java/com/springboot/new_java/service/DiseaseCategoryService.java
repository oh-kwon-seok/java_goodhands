package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.care.CareHomeTypeDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.disease.DiseaseCategoryDto;

import com.springboot.new_java.data.entity.User;

import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.data.entity.disease.DiseaseCategory;

import com.springboot.new_java.data.repository.diseaseCategory.DiseaseCategoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiseaseCategoryService extends AbstractCacheableSearchService<DiseaseCategory, DiseaseCategoryDto> {

    private final UserRepository userRepository;
    private final DiseaseCategoryRepository diseaseCategoryRepository;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DiseaseCategoryService.class);

    public DiseaseCategoryService(UserRepository userRepository, DiseaseCategoryRepository diseaseCategoryRepository, RedisTemplate<String,Object> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
        this.userRepository = userRepository;
        this.diseaseCategoryRepository = diseaseCategoryRepository;
    }

    @Override
    public String getEntityType() {
        return "DiseaseCategory";
    }

    @Override
    public List<DiseaseCategory> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return diseaseCategoryRepository.findAll(searchDto);
    }

    @Override
    protected String[] getRelatedEntityTypes() {
        return new String[]{"User"};
    }

    @Transactional
    public DiseaseCategory save(DiseaseCategoryDto diseaseCategoryDto) {
        try {
            // 1. DiseaseCategory 생성
            DiseaseCategory savedDiseaseCategory = performSave(diseaseCategoryDto);
            LOGGER.info("DiseaseCategory 생성 완료: ID={}, Name={}", savedDiseaseCategory.getUid(), savedDiseaseCategory.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("DiseaseCategory 생성 후 캐시 무효화 완료: {}", savedDiseaseCategory.getUid());

            return savedDiseaseCategory;

        } catch (Exception e) {
            LOGGER.error("DiseaseCategory 생성 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("DiseaseCategory 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    @Transactional
    public DiseaseCategory update(DiseaseCategoryDto diseaseCategoryDto) {
        try {
            // 1. DiseaseCategory 수정
            DiseaseCategory updatedDiseaseCategory = performUpdate(diseaseCategoryDto);

            if (updatedDiseaseCategory == null) {
                LOGGER.warn("수정할 DiseaseCategory를 찾을 수 없습니다: ID={}", diseaseCategoryDto.getUid());
                return null;
            }

            LOGGER.info("DiseaseCategory 수정 완료: ID={}, Name={}", updatedDiseaseCategory.getUid(), updatedDiseaseCategory.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("DiseaseCategory 수정 후 캐시 무효화 완료: {}", updatedDiseaseCategory.getUid());

            return updatedDiseaseCategory;

        } catch (Exception e) {
            LOGGER.error("DiseaseCategory 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("DiseaseCategory 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }

    public DiseaseCategory performSave(DiseaseCategoryDto diseaseCategoryDto) {
        String diseaseCategoryName = diseaseCategoryDto.getName();
        // 기존에 동일한 이름이 사용중인지 체크
        DiseaseCategory existing = diseaseCategoryRepository.findByNameAndUsed(diseaseCategoryName, true);
        if (existing != null) {
            throw new IllegalArgumentException("이미 등록된 질병 유형입니다: " + diseaseCategoryName);
        }

        Optional<User> user = userRepository.findByUid(diseaseCategoryDto.getUser_id());
        DiseaseCategory diseaseCategory = new DiseaseCategory();
        diseaseCategory.setCode(diseaseCategoryDto.getCode());
        diseaseCategory.setName(diseaseCategoryDto.getName());
        diseaseCategory.setUser(user.get());
        diseaseCategory.setDescription(diseaseCategoryDto.getDescription());
        diseaseCategory.setUsed(diseaseCategoryDto.getUsed());
        diseaseCategory.setCreated(LocalDateTime.now());

        return diseaseCategoryRepository.save(diseaseCategory);
    }

    public DiseaseCategory performUpdate(DiseaseCategoryDto diseaseCategoryDto) {
        DiseaseCategory diseaseCategory = diseaseCategoryRepository.findById(diseaseCategoryDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 질병 유형입니다."));

        String diseaseCategoryName = diseaseCategoryDto.getName();
        DiseaseCategory existing = diseaseCategoryRepository.findByNameAndUsed(diseaseCategoryName, true);

        // 다른 UID에서 동일한 이름이 사용 중이면 예외 처리
        if (existing != null && !existing.getUid().equals(diseaseCategoryDto.getUid())) {
            throw new IllegalArgumentException("이미 등록된 질병유형입니다: " + diseaseCategoryName);
        }

        Optional<User> user = userRepository.findByUid(diseaseCategoryDto.getUser_id());
        diseaseCategory.setCode(diseaseCategoryDto.getCode());
        diseaseCategory.setName(diseaseCategoryDto.getName());
        diseaseCategory.setUser(user.get());
        diseaseCategory.setDescription(diseaseCategoryDto.getDescription());
        diseaseCategory.setUsed(diseaseCategoryDto.getUsed());
        diseaseCategory.setUpdated(LocalDateTime.now());

        return diseaseCategoryRepository.save(diseaseCategory);
    }

    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<DiseaseCategory> existingDiseaseCategory = diseaseCategoryRepository.findById(uid);
                if (existingDiseaseCategory.isPresent()) {
                    DiseaseCategory diseaseCategory = existingDiseaseCategory.get();
                    diseaseCategory.setUsed(false);
                    diseaseCategory.setDeleted(LocalDateTime.now());
                    diseaseCategoryRepository.save(diseaseCategory);
                    deletedCount++;
                    LOGGER.debug("DiseaseCategory 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 DiseaseCategory를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("DiseaseCategory 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("DiseaseCategory 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("DiseaseCategory 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("DiseaseCategory 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }

    public DiseaseCategoryDto convertToDto(DiseaseCategory diseaseCategory) {
        DiseaseCategoryDto dto = new DiseaseCategoryDto();
        dto.setUid(diseaseCategory.getUid());
        dto.setName(diseaseCategory.getName());
        dto.setCode(diseaseCategory.getCode());
        dto.setDescription(diseaseCategory.getDescription());
        dto.setUsed(diseaseCategory.getUsed());
        dto.setCreated(LocalDateTime.now());

        if (diseaseCategory.getUser() != null) {
            dto.setUser(diseaseCategory.getUser());
            dto.setUser_id(diseaseCategory.getUser().getUid());
        }

        return dto;
    }

    /**
     * DiseaseCategory 캐시 상태 조회
     */
    public CacheStatus getDiseaseCategoryCacheStatus() {
        return getCacheStatus();
    }
}