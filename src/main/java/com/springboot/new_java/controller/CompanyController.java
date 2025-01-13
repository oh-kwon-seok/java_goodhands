package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.company.CompanyDto;
import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.service.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
@Controller
public class CompanyController {
    private final CompanyService companyService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<Company>> getTotalCompany(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Company> selectedTotalCompany = companyService.getTotalCompany(commonInfoSearchDto);

        LOGGER.info("[getTotalCompany] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalCompany);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Company>> getCompany(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Company> selectedTotalCompany = companyService.getCompany(commonInfoSearchDto);

        LOGGER.info("[getCompany] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalCompany);

    }
    @GetMapping(value= "/customer_select")
    public ResponseEntity<List<Company>> getCustomer(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Company> selectedTotalCustomer = companyService.getCustomer(commonInfoSearchDto);

        LOGGER.info("[customer_select] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalCustomer);

    }


    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto companyDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[companyDto]  : {}", companyDto);
        Company insertCompany = companyService.saveCompany(companyDto);
        LOGGER.info("[createCompany] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(insertCompany);
    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Company> updateCompany(@RequestBody CompanyDto companyDto)
            throws Exception{

        Company updateCompany = companyService.updateCompany(companyDto);
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[companyDto]  : {}", companyDto);

        LOGGER.info("[updateCompany] response Time : {}ms", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(updateCompany);
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteCompany(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        companyService.deleteCompany(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadCompany(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        companyService.excelUploadCompany(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 업로드되었습니다.");
    }

}
