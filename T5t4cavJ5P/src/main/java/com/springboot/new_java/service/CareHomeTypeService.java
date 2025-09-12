package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.care.CareHomeTypeDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.data.repository.careHomeType.CareHomeTypeRepository;
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




    public CareHomeType insertCareHomeType(CareHomeTypeDto careHomeDto) {
        String careHomeTypeName = careHomeDto.getName();
        // 기존에 동일한 이름이 사용중인지 체크
        CareHomeType existing = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);
        if (existing != null) {
            throw new IllegalArgumentException("이미 등록된 시설 유형입니다: " + careHomeTypeName);
        }
        User user = userRepository.getById(careHomeDto.getUser_id());
        CareHomeType careHomeType = new CareHomeType();
        careHomeType.setName(careHomeDto.getName());
        careHomeType.setUser(user);
        careHomeType.setUsed(careHomeDto.getUsed());
        careHomeType.setCreated(LocalDateTime.now());

        return careHomeTypeRepository.save(careHomeType);
    }


    public CareHomeType updateCareHomeType(CareHomeTypeDto careHomeTypeDto) {
        CareHomeType careHome = careHomeTypeRepository.findById(careHomeTypeDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String careHomeTypeName = careHomeTypeDto.getName();
        CareHomeType existing = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);

        // 다른 UID에서 동일한 이름이 사용 중이면 예외 처리
        if (existing != null && !existing.getUid().equals(careHomeTypeDto.getUid())) {
            throw new IllegalArgumentException("이미 등록된 시설유형입니다: " + careHomeTypeName);
        }
        User user = userRepository.getById(careHomeTypeDto.getUser_id());

        careHome.setName(careHomeTypeDto.getName());
        careHome.setUser(user);
        careHome.setUsed(careHomeTypeDto.getUsed());
        careHome.setUpdated(LocalDateTime.now());

        return careHomeTypeRepository.save(careHome);
    }


    public String deleteCareHomeType(List<Long> uids) {
        for (Long uid : uids) {
            CareHomeType careHome = careHomeTypeRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("CareHomeType with UID " + uid + " not found."));

            careHome.setUsed(false);
            careHome.setDeleted(LocalDateTime.now());
            careHomeTypeRepository.save(careHome);
        }
        return "CareHomeTypes deleted successfully";
    }
    public CareHomeTypeDto convertToDto(CareHomeType careHomeType) {
        CareHomeTypeDto dto = new CareHomeTypeDto();
        dto.setUid(careHomeType.getUid());
        dto.setName(careHomeType.getName());

        return dto;
    }
}