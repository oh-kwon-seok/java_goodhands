package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.senior.SeniorDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
//@Tag(name = "Department API", description = "부서 등록/수정/조회/삭제 API")
public class DepartmentController extends AbstractSearchController<Department, DepartmentDto>{

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService,
                                RedisTemplate<String, Object> redisTemplate,
                                ObjectMapper objectMapper) {
        super(departmentService, redisTemplate, objectMapper); // 이 줄이 필수!

        this.departmentService = departmentService;

    }
    @Override
    protected String getEntityPath() {
        return "departments";
    }
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Department>> createDepartment(@RequestBody DepartmentDto departmentDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[departmentDto] : {}", departmentDto);

        Department result = departmentService.save(departmentDto);

        LOGGER.info("[createDepartment] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Department>> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[departmentDto] : {}", departmentDto);

        Department result = departmentService.update(departmentDto);

        LOGGER.info("[updateDepartment] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteDepartment(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        departmentService.delete(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
