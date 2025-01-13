package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.equipmentRuntime.EquipmentRuntimeDto;
import com.springboot.new_java.data.entity.EquipmentRuntime;
import com.springboot.new_java.service.EquipmentRuntimeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/equipment_runtime")
@Controller
public class EquipmentRuntimeController {
    private final EquipmentRuntimeService equipmentRuntimeService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EquipmentRuntimeController.class);

    @Autowired
    public EquipmentRuntimeController(EquipmentRuntimeService equipmentRuntimeService){
        this.equipmentRuntimeService = equipmentRuntimeService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<EquipmentRuntime>> getEquipmentRuntime(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<EquipmentRuntime> selectedEquipmentRuntime = equipmentRuntimeService.getEquipmentRuntime(commonInfoSearchDto);

        LOGGER.info("[getTotalEquipmentRuntimeSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedEquipmentRuntime);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<EquipmentRuntime>> getTotalEquipmentRuntime(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<EquipmentRuntime> selectedTotalEquipmentRuntime = equipmentRuntimeService.getTotalEquipmentRuntime(commonSearchDto);

        LOGGER.info("[getTotalEquipmentRuntime] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalEquipmentRuntime);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createEquipmentRuntime(@RequestBody EquipmentRuntimeDto equipmentRuntimeDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[equipmentRuntimeDto]  : {}", equipmentRuntimeDto);
        CommonResultDto equipmentRuntimeResultDto = equipmentRuntimeService.saveEquipmentRuntime(equipmentRuntimeDto);
        LOGGER.info("[createEquipmentRuntime] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  equipmentRuntimeResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateEquipmentRuntime(@RequestBody EquipmentRuntimeDto equipmentRuntimeDto)
            throws Exception{
        CommonResultDto equipmentRuntimeResultDto = equipmentRuntimeService.updateEquipmentRuntime(equipmentRuntimeDto);
        return equipmentRuntimeResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        equipmentRuntimeService.deleteEquipmentRuntime(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
