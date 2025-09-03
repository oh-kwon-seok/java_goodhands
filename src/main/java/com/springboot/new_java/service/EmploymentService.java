package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.employment.EmploymentDto;
import com.springboot.new_java.data.dto.employment.EmploymentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.Employment;
import com.springboot.new_java.data.entity.Employment;
import com.springboot.new_java.data.repository.employment.EmploymentRepository;
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
public class EmploymentService extends AbstractCacheableSearchService<Employment, EmploymentDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EmploymentService.class);

    private final EmploymentRepository employmentRepository;

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


    public Employment insertEmployment(EmploymentDto employmentDto) {
        Employment employment = new Employment();
        employment.setName(employmentDto.getName());
        employment.setName2(employmentDto.getName2());
        employment.setUsed(employmentDto.getUsed());
        employment.setCreated(LocalDateTime.now());
        return employmentRepository.save(employment);
    }



    public Employment updateEmployment(EmploymentDto employmentDto) {
        Employment employment = employmentRepository.findById(employmentDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 직책입니다."));

        employment.setName(employmentDto.getName());
        employment.setName2(employmentDto.getName2());
        employment.setUsed(employmentDto.getUsed());
        employment.setUpdated(LocalDateTime.now());

        return employmentRepository.save(employment);
    }

    public String deleteEmployment(List<Long> uids) {
        for (Long uid : uids) {
            Employment employment = employmentRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("Employment with UID " + uid + " not found."));

            employment.setUsed(false);
            employment.setDeleted(LocalDateTime.now());
            employmentRepository.save(employment);
        }
        return "Employments deleted successfully";
    }

    public EmploymentDto convertToDto(Employment employment) {
        EmploymentDto dto = new EmploymentDto();
        dto.setUid(employment.getUid());
        dto.setName(employment.getName());

        return dto;
    }
}