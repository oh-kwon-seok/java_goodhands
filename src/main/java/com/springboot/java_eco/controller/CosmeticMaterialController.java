package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;

import com.springboot.java_eco.data.dto.costmeticMaterial.CosmeticMaterialDto;
import com.springboot.java_eco.data.entity.CosmeticMaterial;
import com.springboot.java_eco.service.CosmeticMaterialService;
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
@RequestMapping("/cosmetic_material")
@Controller
public class CosmeticMaterialController {
    private final CosmeticMaterialService cosmeticMaterialService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CosmeticMaterialController.class);

    @Autowired
    public CosmeticMaterialController(CosmeticMaterialService cosmeticMaterialService){
        this.cosmeticMaterialService = cosmeticMaterialService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<CosmeticMaterial>> getTotalCosmeticMaterial(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<CosmeticMaterial> selectedTotalCosmeticMaterial = cosmeticMaterialService.getTotalCosmeticMaterial(commonInfoSearchDto);

        LOGGER.info("[getTotalCosmeticMaterial] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalCosmeticMaterial);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<CosmeticMaterial>> getCosmeticMaterial(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<CosmeticMaterial> selectedTotalCosmeticMaterial = cosmeticMaterialService.getCosmeticMaterial(commonInfoSearchDto);

        LOGGER.info("[getTotalCosmeticMaterial] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalCosmeticMaterial);

    }

    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createCosmeticMaterial(@RequestBody CosmeticMaterialDto cosmeticMaterialDto, HttpServletRequest request
    ) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[cosmeticMaterialDto]  : {}", cosmeticMaterialDto);


        CommonResultDto cosmeticMaterialResultDto = cosmeticMaterialService.saveCosmeticMaterial(cosmeticMaterialDto,request.getRemoteAddr());
        LOGGER.info("[createCosmeticMaterial] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return cosmeticMaterialResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteCosmeticMaterial(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        cosmeticMaterialService.deleteCosmeticMaterial(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
  

}
