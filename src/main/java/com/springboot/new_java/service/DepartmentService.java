package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.repository.department.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.List;


@Service
public class DepartmentService extends AbstractCacheableSearchService<Department, DepartmentDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;


    public DepartmentService(DepartmentRepository departmentRepository,
                             RedisTemplate<String, Object> redisTemplate,
                             ObjectMapper objectMapper) {
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



    public Department insertDepartment(DepartmentDto departmentDto) {

        Department department = new Department();

        department.setName(departmentDto.getName());
        department.setUsed(departmentDto.getUsed());
        department.setCreated(LocalDateTime.now());

        return departmentRepository.save(department);
    }



    public Department updateDepartment(DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 부서입니다."));

        department.setName(departmentDto.getName());
        department.setUsed(departmentDto.getUsed());
        department.setUpdated(LocalDateTime.now());

        return departmentRepository.save(department);
    }

    public String deleteDepartment(List<Long> uids) {
        for (Long uid : uids) {
            Department department = departmentRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("Department with UID " + uid + " not found."));

            department.setUsed(false);
            department.setDeleted(LocalDateTime.now());
            departmentRepository.save(department);
        }
        return "Departments deleted successfully";
    }

    @Override
    public DepartmentDto convertToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setUid(department.getUid());
        dto.setName(department.getName());
        dto.setUsed(department.getUsed());
        dto.setCreated(department.getCreated());
        return dto;
    }
}
