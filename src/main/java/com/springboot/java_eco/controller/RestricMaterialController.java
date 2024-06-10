package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.restricMaterial.RestricMaterialDto;
import com.springboot.java_eco.data.entity.RestricMaterial;
import com.springboot.java_eco.service.RestricMaterialService;
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
@RequestMapping("/restric_material")
@Controller
public class RestricMaterialController {
    private final RestricMaterialService restricMaterialService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(RestricMaterialController.class);

    @Autowired
    public RestricMaterialController(RestricMaterialService restricMaterialService){
        this.restricMaterialService = restricMaterialService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<RestricMaterial>> getTotalRestricMaterial(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<RestricMaterial> selectedTotalRestricMaterial = restricMaterialService.getTotalRestricMaterial(commonInfoSearchDto);

        LOGGER.info("[getTotalRestricMaterial] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalRestricMaterial);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<RestricMaterial>> getRestricMaterial(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<RestricMaterial> selectedTotalRestricMaterial = restricMaterialService.getRestricMaterial(commonInfoSearchDto);

        LOGGER.info("[getTotalRestricMaterial] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalRestricMaterial);

    }

    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createRestricMaterial(@RequestBody RestricMaterialDto restricMaterialDto, HttpServletRequest request
    ) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[restricMaterialDto]  : {}", restricMaterialDto);


        CommonResultDto restricMaterialResultDto = restricMaterialService.saveRestricMaterial(restricMaterialDto,request.getRemoteAddr());
        LOGGER.info("[createRestricMaterial] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return restricMaterialResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteRestricMaterial(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        restricMaterialService.deleteRestricMaterial(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
  

}
