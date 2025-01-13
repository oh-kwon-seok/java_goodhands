package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.service.DepartmentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
@Controller
public class DepartmentController {
    private final DepartmentService departmentService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<Department>> getTotalDepartment(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Department> selectedTotalDepartment = departmentService.getTotalDepartment(commonInfoSearchDto);

        LOGGER.info("[getTotalDepartment] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalDepartment);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Department>> getDepartment(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Department> selectedTotalDepartment = departmentService.getDepartment(commonInfoSearchDto);

        LOGGER.info("[getTotalDepartment] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalDepartment);

    }

    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDto departmentDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[departmentDto]  : {}", departmentDto);
        Department insertDepartment = departmentService.saveDepartment(departmentDto);
        LOGGER.info("[createDepartment] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(insertDepartment);
    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Department> updateDepartment(@RequestBody DepartmentDto departmentDto)
            throws Exception{

        Department updateDepartment = departmentService.updateDepartment(departmentDto);
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[departmentDto]  : {}", departmentDto);

        LOGGER.info("[updateDepartment] response Time : {}ms", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(updateDepartment);
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteDepartment(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        departmentService.deleteDepartment(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
