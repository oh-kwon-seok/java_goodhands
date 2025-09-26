package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.care.CareHomeDto;


import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHome;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.data.repository.careHome.CareHomeRepository;
import com.springboot.new_java.data.repository.careHomeType.CareHomeTypeRepository;
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
public class CareHomeService extends AbstractCacheableSearchService<CareHome, CareHomeDto>{
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CareHomeService.class);

    private final CareHomeRepository careHomeRepository;
    private final UserRepository userRepository;
    private final CareHomeTypeRepository careHomeTypeRepository;




    public CareHomeService(CareHomeRepository careHomeRepository, UserRepository userRepository, CareHomeTypeRepository careHomeTypeRepository,CareHomeTypeService careHomeTypeService, RedisTemplate<String,Object> redisTemplate,ObjectMapper objectMapper) {

        super(redisTemplate,objectMapper);
        this.careHomeRepository = careHomeRepository;
        this.userRepository = userRepository;
        this.careHomeTypeRepository = careHomeTypeRepository;

    }

    @Override
    public String getEntityType() {
        return "CareHome";
    }

    @Override
    public List<CareHome> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return careHomeRepository.findAll(searchDto);
    }

    @Transactional
    public CareHome save(CareHomeDto careHomeDto) {
        try {
            // 1. CareHome 생성
            CareHome savedCareHome = performSave(careHomeDto);
            LOGGER.info("CareHome 생성 완료: ID={}, Name={}", savedCareHome.getUid(), savedCareHome.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("CareHome 생성 후 캐시 무효화 완료: {}", savedCareHome.getUid());

            return savedCareHome;

        } catch (Exception e) {
            LOGGER.error("CareHome 생성 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareHome 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    @Transactional
    public CareHome update(CareHomeDto careHomeDto) {
        try {
            // 1. CareHome 수정
            CareHome updatedCareHome = performUpdate(careHomeDto);

            if (updatedCareHome == null) {
                LOGGER.warn("수정할 CareHome를 찾을 수 없습니다: ID={}", careHomeDto.getUid());
                return null;
            }

            LOGGER.info("CareHome 수정 완료: ID={}, Name={}", updatedCareHome.getUid(), updatedCareHome.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("CareHome 수정 후 캐시 무효화 완료: {}", updatedCareHome.getUid());

            return updatedCareHome;

        } catch (Exception e) {
            LOGGER.error("CareHome 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareHome 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }

    public CareHome performSave(CareHomeDto careHomeDto) {
        CareHome careHome = new CareHome();
        CareHomeType careHomeType = careHomeTypeRepository.findByUid(careHomeDto.getCareHomeType().getUid());

        User user = userRepository.getById(careHomeDto.getUser_id());
        careHome.setName(careHomeDto.getName());
        careHome.setCareHomeType(careHomeType);
        careHome.setUser(user);

        careHome.setUsed(careHomeDto.getUsed());
        careHome.setCreated(LocalDateTime.now());
        return careHomeRepository.save(careHome);
    }


    public CareHome performUpdate(CareHomeDto careHomeDto) {
        CareHome careHome = careHomeRepository.findById(careHomeDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        CareHomeType careHomeType = careHomeTypeRepository.findByUid(careHomeDto.getCare_home_type_uid());

        careHome.setCareHomeType(careHomeType);
        careHome.setName(careHomeDto.getName());
        careHome.setUsed(careHomeDto.getUsed());
        careHome.setUpdated(LocalDateTime.now());

        return careHomeRepository.save(careHome);
    }

    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<CareHome> existingCareHome = careHomeRepository.findById(uid);
                if (existingCareHome.isPresent()) {
                    CareHome careHome = existingCareHome.get();
                    careHome.setUsed(false);
                    careHome.setDeleted(LocalDateTime.now());
                    careHomeRepository.save(careHome);
                    deletedCount++;
                    LOGGER.debug("CareHome 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 CareHome를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("CareHome 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("CareHome 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("CareHome 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareHome 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }


    @Override
    public CareHomeDto convertToDto(CareHome careHome) {

        CareHomeDto dto = new CareHomeDto();
        dto.setUid(careHome.getUid());
        dto.setName(careHome.getName());
        dto.setUsed(careHome.getUsed());


        dto.setCare_home_type_uid(careHome.getCareHomeType().getUid());
        dto.setUser(careHome.getUser());

        return dto;
    }
}