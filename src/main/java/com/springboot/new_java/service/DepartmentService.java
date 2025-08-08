package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.repository.department.DepartmentRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    public Department insertDepartment(DepartmentDto departmentDto) {
        User user = userRepository.findById(departmentDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

        Department department = new Department();
        department.setUser(user);
        department.setName(departmentDto.getName());
        department.setUsed(departmentDto.getUsed());
        department.setCreated(LocalDateTime.now());

        return departmentRepository.save(department);
    }

    public List<Department> getTotalDepartment(CommonInfoSearchDto commonInfoSearchDto) {
        return departmentRepository.findAll(commonInfoSearchDto);
    }

    public List<Department> getDepartment(CommonInfoSearchDto commonInfoSearchDto) {
        return departmentRepository.findInfo(commonInfoSearchDto);
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
}
