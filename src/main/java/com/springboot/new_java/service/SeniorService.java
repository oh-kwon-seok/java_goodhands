package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.controller.SeniorController;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.senior.SeniorDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.User;

import com.springboot.new_java.data.entity.senior.Senior;

import com.springboot.new_java.data.repository.careHome.CareHomeRepository;
import com.springboot.new_java.data.repository.senior.SeniorRepository;

import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.Cacheable;
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
    public Senior insertSenior(SeniorDto seniorDto) {
        Senior senior = new Senior();
        String userId = seniorDto.getUser_id();
        String caregiverId = seniorDto.getCaregiver_id();

        User user = userRepository.getById(userId);
        User caregiver = userRepository.getById(caregiverId);
        senior.setName(seniorDto.getName());
        senior.setBirth_date(seniorDto.getName());
        senior.setUser(user);
        senior.setCaregiver(caregiver);
        senior.setUse_care_schedule(seniorDto.getUse_care_schedule());
        senior.setCare_schedule_protocol(seniorDto.getCare_schedule_protocol());


        senior.setUsed(seniorDto.getUsed());
        senior.setCreated(LocalDateTime.now());
        return seniorRepository.save(senior);
    }
    public Senior updateSenior(SeniorDto seniorDto) {
        Senior senior = seniorRepository.findById(seniorDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String userId = seniorDto.getUser_id();
        String caregiverId = seniorDto.getCaregiver_id();

        User user = userRepository.getById(userId);
        User caregiver = userRepository.getById(caregiverId);
        senior.setName(seniorDto.getName());
        senior.setBirth_date(seniorDto.getName());
        senior.setUser(user);
        senior.setCaregiver(caregiver);
        senior.setUse_care_schedule(seniorDto.getUse_care_schedule());
        senior.setCare_schedule_protocol(seniorDto.getCare_schedule_protocol());

        senior.setUsed(seniorDto.getUsed());
        senior.setUpdated(LocalDateTime.now());

        return seniorRepository.save(senior);
    }

    public String deleteSenior(List<Long> uids) {
        for (Long uid : uids) {
            Senior senior = seniorRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("Senior with UID " + uid + " not found."));

            senior.setUsed(false);
            senior.setDeleted(LocalDateTime.now());
            seniorRepository.save(senior);
        }
        return "Seniors deleted successfully";
    }

    public SeniorDto convertToDto(Senior senior) {
        SeniorDto dto = new SeniorDto();
        dto.setUid(senior.getUid());
        dto.setName(senior.getName());

        return dto;
    }
}