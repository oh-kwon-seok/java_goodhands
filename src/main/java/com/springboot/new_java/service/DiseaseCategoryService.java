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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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
        return " DiseaseCategory";
    }
    @Override
    public List<DiseaseCategory> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return diseaseCategoryRepository.findAll(searchDto);
    }

    public DiseaseCategory insertDiseaseCategory(DiseaseCategoryDto diseaseCategoryDto) {
        String diseaseCategoryName = diseaseCategoryDto.getName();
        // 기존에 동일한 이름이 사용중인지 체크
        DiseaseCategory existing = diseaseCategoryRepository.findByNameAndUsed(diseaseCategoryName, true);
        if (existing != null) {
            throw new IllegalArgumentException("이미 등록된 시설 유형입니다: " + diseaseCategoryName);
        }
        User user = userRepository.getById(diseaseCategoryDto.getUser_id());
        DiseaseCategory diseaseCategory = new DiseaseCategory();
        diseaseCategory.setCode(diseaseCategoryDto.getCode());
        diseaseCategory.setName(diseaseCategoryDto.getName());
        diseaseCategory.setUser(user);
        diseaseCategory.setDescription(diseaseCategoryDto.getDescription());
        diseaseCategory.setUsed(diseaseCategoryDto.getUsed());
        diseaseCategory.setCreated(LocalDateTime.now());

        return diseaseCategoryRepository.save(diseaseCategory);
    }


    public List<DiseaseCategoryDto> getDiseaseCategoryWithCache(String cacheKey) {
        long startTime = System.currentTimeMillis();

        try {
            // 1. Redis에서 캐시 확인
            LOGGER.debug("🔍 Redis 캐시 확인: {}", cacheKey);
            String cachedData = (String) redisTemplate.opsForValue().get(cacheKey);

            if (cachedData != null) {
                LOGGER.info("✅ 캐시 HIT - Redis에서 데이터 반환 ({}ms)",
                        System.currentTimeMillis() - startTime);
                return objectMapper.readValue(cachedData,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, DiseaseCategoryDto.class));
            }

            // 2. 캐시 MISS - MariaDB에서 조회
            LOGGER.info("🔄 캐시 MISS - DB에서 데이터 조회 시작");
            List<DiseaseCategory> diseaseCategorys = diseaseCategoryRepository.findAll();
            List<DiseaseCategoryDto> diseaseCategoryDtos = diseaseCategorys.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            // 3. Redis에 새로 캐싱 (5분 TTL)
            try {
                String jsonData = objectMapper.writeValueAsString(diseaseCategoryDtos);
                redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofMinutes(5));
                LOGGER.info("💾 새 데이터를 Redis에 캐싱 완료 (TTL: 5분, 데이터 수: {}개)", diseaseCategoryDtos.size());
            } catch (Exception e) {
                LOGGER.error("Redis 캐싱 실패: {}", e.getMessage());
            }

            LOGGER.info("✅ DB 조회 완료 (총 처리시간: {}ms)",
                    System.currentTimeMillis() - startTime);
            return diseaseCategoryDtos;

        } catch (Exception e) {
            LOGGER.error("getDiseaseCategoryWithCache 처리 중 오류: {}", e.getMessage(), e);
            // 캐시 실패해도 DB에서라도 조회 시도
            return diseaseCategoryRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
    }



    public List<DiseaseCategory> getTotalDiseaseCategory(CommonInfoSearchDto commonInfoSearchDto) {
        return diseaseCategoryRepository.findAll(commonInfoSearchDto);
    }

    public List<DiseaseCategory> getDiseaseCategory(CommonInfoSearchDto commonInfoSearchDto) {
        return diseaseCategoryRepository.findInfo(commonInfoSearchDto);
    }

    public DiseaseCategory updateDiseaseCategory(DiseaseCategoryDto diseaseCategoryDto) {
        DiseaseCategory diseaseCategory = diseaseCategoryRepository.findById(diseaseCategoryDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String diseaseCategoryName = diseaseCategoryDto.getName();
        DiseaseCategory existing = diseaseCategoryRepository.findByNameAndUsed(diseaseCategoryName, true);

        // 다른 UID에서 동일한 이름이 사용 중이면 예외 처리
        if (existing != null && !existing.getUid().equals(diseaseCategoryDto.getUid())) {
            throw new IllegalArgumentException("이미 등록된 질병유형입니다: " + diseaseCategoryName);
        }
        User user = userRepository.getById(diseaseCategoryDto.getUser_id());
        diseaseCategory.setCode(diseaseCategoryDto.getCode());
        diseaseCategory.setName(diseaseCategoryDto.getName());
        diseaseCategory.setUser(user);
        diseaseCategory.setDescription(diseaseCategoryDto.getDescription());
        diseaseCategory.setUsed(diseaseCategoryDto.getUsed());
        diseaseCategory.setUpdated(LocalDateTime.now());

        return diseaseCategoryRepository.save(diseaseCategory);
    }


    public String deleteDiseaseCategory(List<Long> uids) {
        for (Long uid : uids) {
            DiseaseCategory careHome = diseaseCategoryRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("DiseaseCategory with UID " + uid + " not found."));

            careHome.setUsed(false);
            careHome.setDeleted(LocalDateTime.now());
            diseaseCategoryRepository.save(careHome);
        }
        return "DiseaseCategorys deleted successfully";
    }

    public DiseaseCategoryDto convertToDto(DiseaseCategory diseaseCategory) {
        DiseaseCategoryDto dto = new DiseaseCategoryDto();
        dto.setUid(diseaseCategory.getUid());
        dto.setName(diseaseCategory.getName());

        return dto;
    }
}