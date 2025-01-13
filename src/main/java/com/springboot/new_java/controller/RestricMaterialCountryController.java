package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.restricMaterialCountry.RestricMaterialCountryDto;
import com.springboot.new_java.data.entity.RestricMaterialCountry;
import com.springboot.new_java.service.RestricMaterialCountryService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restric_material_country")
@Controller
public class RestricMaterialCountryController {
    private final RestricMaterialCountryService restricMaterialCountryService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(RestricMaterialCountryController.class);

    @Autowired
    public RestricMaterialCountryController(RestricMaterialCountryService restricMaterialCountryService){
        this.restricMaterialCountryService = restricMaterialCountryService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<RestricMaterialCountry>> getTotalRestricMaterialCountry(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<RestricMaterialCountry> selectedTotalRestricMaterialCountry = restricMaterialCountryService.getTotalRestricMaterialCountry(commonInfoSearchDto);

        LOGGER.info("[getTotalRestricMaterialCountry] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalRestricMaterialCountry);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<RestricMaterialCountry>> getRestricMaterialCountry(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<RestricMaterialCountry> selectedTotalRestricMaterialCountry = restricMaterialCountryService.getRestricMaterialCountry(commonInfoSearchDto);

        LOGGER.info("[getTotalRestricMaterialCountry] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalRestricMaterialCountry);

    }

    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createRestricMaterialCountry(@RequestBody RestricMaterialCountryDto restricMaterialCountryDto, HttpServletRequest request
    ) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[restricMaterialCountryDto]  : {}", restricMaterialCountryDto);


        CommonResultDto restricMaterialCountryResultDto = restricMaterialCountryService.saveRestricMaterialCountry(restricMaterialCountryDto,request.getRemoteAddr());
        LOGGER.info("[createRestricMaterialCountry] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return restricMaterialCountryResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteRestricMaterialCountry(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        restricMaterialCountryService.deleteRestricMaterialCountry(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
  

}
