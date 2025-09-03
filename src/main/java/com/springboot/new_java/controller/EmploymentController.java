package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.data.dto.care.CareHomeTypeDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.employment.EmploymentDto;
import com.springboot.new_java.data.entity.Employment;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.service.EmploymentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employment")
@Controller
public class EmploymentController extends AbstractSearchController<Employment, EmploymentDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EmploymentController.class);

    private final EmploymentService employmentService;

    public EmploymentController(EmploymentService employmentService, RedisTemplate<String,Object> redisTemplate, ObjectMapper objectMapper){
        super(employmentService,redisTemplate,objectMapper);
        this.employmentService = employmentService;
    }

    @Override
    protected String getEntityPath() {
        return "employments";
    }


    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Employment> createEmployment(@RequestBody EmploymentDto employmentDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[employmentDto]  : {}", employmentDto);
        Employment insertEmployment = employmentService.insertEmployment(employmentDto);
        LOGGER.info("[createEmployment] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(insertEmployment);
    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Employment> updateEmployment(@RequestBody EmploymentDto employmentDto)
            throws Exception{

        Employment updateEmployment = employmentService.updateEmployment(employmentDto);
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[employmentDto]  : {}", employmentDto);

        LOGGER.info("[updateEmployment] response Time : {}ms", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(updateEmployment);
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteEmployment(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        employmentService.deleteEmployment(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
