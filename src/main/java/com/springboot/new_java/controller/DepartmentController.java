package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
//@Tag(name = "Department API", description = "부서 등록/수정/조회/삭제 API")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    //@Operation(summary = "전체 부서 조회(검색가능)", description = "전체 부서를 조회하는 데, 검색기능이 가능합니다.")
    @GetMapping("/select")
    public ResponseEntity<CommonApiResponse<List<Department>>> getTotalDepartment(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) {
        long start = System.currentTimeMillis();
        List<Department> departments = departmentService.getTotalDepartment(commonInfoSearchDto);
        LOGGER.info("[getTotalDepartment] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(departments));
    }
    //@Operation(summary = "전체 부서 조회", description = "검색관계없이 모든 부서를 조회 가능합니다.")
    @GetMapping("/info_select")
    public ResponseEntity<CommonApiResponse<List<Department>>> getDepartment(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) {
        long start = System.currentTimeMillis();
        List<Department> departments = departmentService.getDepartment(commonInfoSearchDto);
        LOGGER.info("[getDepartment] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(departments));
    }
//    @Operation(summary = "부서 등록", description = "신규 부서를 등록합니다.",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            required = true,
//            content = @Content(schema = @Schema(implementation = DepartmentDto.class))
//    ))
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "부서 등록 성공"),
//            @ApiResponse(responseCode = "400", description = "유효하지 않은 사용자 ID")
//    })
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Department>> createDepartment(@RequestBody DepartmentDto departmentDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[departmentDto] : {}", departmentDto);

        Department result = departmentService.insertDepartment(departmentDto);

        LOGGER.info("[createDepartment] response time: {}ms", System.currentTimeMillis() - start);

        if (result == null) {
            return ResponseEntity.badRequest().body(CommonApiResponse.error("유효하지 않은 사용자 ID입니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success(result, "부서 등록 성공"));
    }
//    @Operation(summary = "부서 수정", description = "부서를 수정합니다.",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            required = true,
//            content = @Content(schema = @Schema(implementation = DepartmentDto.class))
//    ))
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "부서 등록 성공"),
//            @ApiResponse(responseCode = "400", description = "유효하지 않은 사용자 ID")
//    })
    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<Department>> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        long start = System.currentTimeMillis();
        LOGGER.info("[departmentDto] : {}", departmentDto);

        Department result = departmentService.updateDepartment(departmentDto);

        LOGGER.info("[updateDepartment] response time: {}ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(CommonApiResponse.success(result, "부서 수정 성공"));
    }
//    @Operation(summary = "부서 삭제", description = "부서를 삭제합니다.",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            required = true,
//            content = @Content(schema = @Schema(implementation = DepartmentDto.class))
//    ))
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "부서 삭제 성공"),
//            @ApiResponse(responseCode = "400", description = "유효하지 않은 사용자 ID")
//    })
    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<String>> deleteDepartment(@RequestBody Map<String, List<Long>> requestMap) {
        List<Long> uid = requestMap.get("uid");
        departmentService.deleteDepartment(uid);
        return ResponseEntity.ok(CommonApiResponse.success("정상적으로 삭제되었습니다."));
    }
}
