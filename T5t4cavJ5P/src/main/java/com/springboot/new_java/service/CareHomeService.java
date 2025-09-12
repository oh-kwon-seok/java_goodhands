package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.care.CareHomeDto;

import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHome;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.data.repository.careHome.CareHomeRepository;
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
    @Override
    public Duration getCacheTtl(CommonInfoSearchDto searchDto) {
        return searchDto.hasSearchCondition()
                ? Duration.ofMinutes(2)  // Department 검색은 2분
                : Duration.ofMinutes(10); // Department 전체는 10분
    }

    public CareHome insertCareHome(CareHomeDto careHomeDto) {
        CareHome careHome = new CareHome();
        String careHomeTypeName = careHomeDto.getName();
        CareHomeType careHomeType = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);
        User user = userRepository.getById(careHomeDto.getUser_id());
        careHome.setName(careHomeDto.getName());
        careHome.setCareHomeType(careHomeType);
        careHome.setUser(user);

        careHome.setUsed(careHomeDto.getUsed());
        careHome.setCreated(LocalDateTime.now());
        return careHomeRepository.save(careHome);
    }


    public CareHome updateCareHome(CareHomeDto careHomeDto) {
        CareHome careHome = careHomeRepository.findById(careHomeDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String careHomeTypeName = careHomeDto.getName();
        CareHomeType careHomeType = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);
        careHome.setCareHomeType(careHomeType);

        careHome.setName(careHomeDto.getName());
        careHome.setUsed(careHomeDto.getUsed());
        careHome.setUpdated(LocalDateTime.now());

        return careHomeRepository.save(careHome);
    }

    public String deleteCareHome(List<Long> uids) {
        for (Long uid : uids) {
            CareHome careHome = careHomeRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("CareHome with UID " + uid + " not found."));

            careHome.setUsed(false);
            careHome.setDeleted(LocalDateTime.now());
            careHomeRepository.save(careHome);
        }
        return "CareHomes deleted successfully";
    }


    @Override
    public CareHomeDto convertToDto(CareHome careHome) {
        CareHomeDto dto = new CareHomeDto();
        dto.setUid(careHome.getUid());
        dto.setName(careHome.getName());

        return dto;
    }
}