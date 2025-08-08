package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.employment.EmploymentDto;
import com.springboot.new_java.data.entity.Employment;
import com.springboot.new_java.data.repository.employment.EmploymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmploymentService {

    private final EmploymentRepository employmentRepository;

    @Autowired
    public EmploymentService(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    public Employment insertEmployment(EmploymentDto employmentDto) {
        Employment employment = new Employment();
        employment.setName(employmentDto.getName());
        employment.setName2(employmentDto.getName2());
        employment.setUsed(employmentDto.getUsed());
        employment.setCreated(LocalDateTime.now());
        return employmentRepository.save(employment);
    }

    public List<Employment> getTotalEmployment(CommonInfoSearchDto commonInfoSearchDto) {
        return employmentRepository.findAll(commonInfoSearchDto);
    }

    public List<Employment> getEmployment(CommonInfoSearchDto commonInfoSearchDto) {
        return employmentRepository.findInfo(commonInfoSearchDto);
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
}