package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.type.TypeDto;
import com.springboot.java_eco.data.entity.Type;
import com.springboot.java_eco.service.TypeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/type")
@Controller
public class TypeController {
    private final TypeService typeService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(TypeController.class);

    @Autowired
    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<Type>> getTotalType(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Type> selectedTotalType = typeService.getTotalType(commonInfoSearchDto);

        LOGGER.info("[getTotalType] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalType);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Type>> getType(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Type> selectedTotalType = typeService.getType(commonInfoSearchDto);

        LOGGER.info("[getTotalType] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalType);

    }

    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Type> createType(@RequestBody TypeDto typeDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[typeDto]  : {}", typeDto);
        Type insertType = typeService.saveType(typeDto);
        LOGGER.info("[createType] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(insertType);
    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Type> updateType(@RequestBody TypeDto typeDto)
            throws Exception{

        Type updateType = typeService.updateType(typeDto);
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[typeDto]  : {}", typeDto);

        LOGGER.info("[updateType] response Time : {}ms", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(updateType);
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteType(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        typeService.deleteType(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
