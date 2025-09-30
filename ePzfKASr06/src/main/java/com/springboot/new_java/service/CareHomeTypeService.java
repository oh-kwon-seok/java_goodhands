package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.care.CareHomeTypeDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;



import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.data.repository.careHomeType.CareHomeTypeRepository;
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
public class CareHomeTypeService extends AbstractCacheableSearchService<CareHomeType, CareHomeTypeDto>{

    
    private final UserRepository userRepository;
    private final CareHomeTypeRepository careHomeTypeRepository;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CareHomeTypeService.class);



    public CareHomeTypeService( UserRepository userRepository,
                                CareHomeTypeRepository careHomeTypeRepository,
                                RedisTemplate<String,Object> redisTemplate,
                                ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
        this.userRepository = userRepository;
        this.careHomeTypeRepository = careHomeTypeRepository;

    }

    @Override
    public String getEntityType() {
        return "CareHomeType";
    }
    @Override
    public List<CareHomeType> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return careHomeTypeRepository.findAll(searchDto);
    }



    @Transactional
    public CareHomeType save(CareHomeTypeDto careHomeTypeDto) {
        try {
            // 1. CareHomeType 생성
            CareHomeType savedCareHomeType = performSave(careHomeTypeDto);
            LOGGER.info("CareHomeType 생성 완료: ID={}, Name={}", savedCareHomeType.getUid(), savedCareHomeType.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("CareHomeType 생성 후 캐시 무효화 완료: {}", savedCareHomeType.getUid());

            return savedCareHomeType;

        } catch (Exception e) {
            LOGGER.error("CareHomeType 생성 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareHomeType 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    @Transactional
    public CareHomeType update(CareHomeTypeDto careHomeTypeDto) {
        try {
            // 1. CareHomeType 수정
            CareHomeType updatedCareHomeType = performUpdate(careHomeTypeDto);

            if (updatedCareHomeType == null) {
                LOGGER.warn("수정할 CareHomeType를 찾을 수 없습니다: ID={}", careHomeTypeDto.getUid());
                return null;
            }

            LOGGER.info("CareHomeType 수정 완료: ID={}, Name={}", updatedCareHomeType.getUid(), updatedCareHomeType.getName());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("CareHomeType 수정 후 캐시 무효화 완료: {}", updatedCareHomeType.getUid());

            return updatedCareHomeType;

        } catch (Exception e) {
            LOGGER.error("CareHomeType 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareHomeType 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }


    public CareHomeType performSave(CareHomeTypeDto careHomeDto) {
        String careHomeTypeName = careHomeDto.getName();
        // 기존에 동일한 이름이 사용중인지 체크
        CareHomeType existing = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);
        if (existing != null) {
            throw new IllegalArgumentException("이미 등록된 시설 유형입니다: " + careHomeTypeName);
        }
        Optional<User> user = userRepository.findByUid(careHomeDto.getUser_id());
        CareHomeType careHomeType = new CareHomeType();
        careHomeType.setName(careHomeDto.getName());
        careHomeType.setUser(user.get());
        careHomeType.setUsed(careHomeDto.getUsed());
        careHomeType.setCreated(LocalDateTime.now());

        return careHomeTypeRepository.save(careHomeType);
    }


    public CareHomeType performUpdate(CareHomeTypeDto careHomeTypeDto) {
        CareHomeType careHome = careHomeTypeRepository.findById(careHomeTypeDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String careHomeTypeName = careHomeTypeDto.getName();
        CareHomeType existing = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);

        // 다른 UID에서 동일한 이름이 사용 중이면 예외 처리
        if (existing != null && !existing.getUid().equals(careHomeTypeDto.getUid())) {
            throw new IllegalArgumentException("이미 등록된 시설유형입니다: " + careHomeTypeName);
        }
        Optional<User> user = userRepository.findByUid(careHomeTypeDto.getUser_id());

        careHome.setName(careHomeTypeDto.getName());
        careHome.setUser(user.get());
        careHome.setUsed(careHomeTypeDto.getUsed());
        careHome.setUpdated(LocalDateTime.now());

        return careHomeTypeRepository.save(careHome);
    }


    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<CareHomeType> existingCareHomeType = careHomeTypeRepository.findById(uid);
                if (existingCareHomeType.isPresent()) {
                    CareHomeType careHomeType = existingCareHomeType.get();
                    careHomeType.setUsed(false);
                    careHomeType.setDeleted(LocalDateTime.now());
                    careHomeTypeRepository.save(careHomeType);
                    deletedCount++;
                    LOGGER.debug("CareHomeType 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 CareHomeType를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("CareHomeType 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("CareHomeType 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("CareHomeType 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareHomeType 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }
    public CareHomeTypeDto convertToDto(CareHomeType careHomeType) {
        CareHomeTypeDto dto = new CareHomeTypeDto();
        dto.setUid(careHomeType.getUid());
        dto.setName(careHomeType.getName());
        dto.setUser(careHomeType.getUser());
        dto.setCreated(careHomeType.getCreated());


        return dto;
    }
}