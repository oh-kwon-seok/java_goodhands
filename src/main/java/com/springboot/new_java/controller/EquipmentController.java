package com.springboot.new_java.controller;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.equipment.EquipmentDto;
import com.springboot.new_java.data.entity.Equipment;
import com.springboot.new_java.service.EquipmentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/equipment")
@Controller
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    public EquipmentController(EquipmentService equipmentService){
        this.equipmentService = equipmentService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<Equipment>> getTotalEquipment(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Equipment> selectedTotalEquipment = equipmentService.getTotalEquipment(commonInfoSearchDto);

        LOGGER.info("[getTotalEquipment] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalEquipment);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Equipment>> getEquipment(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Equipment> selectedTotalEquipment = equipmentService.getEquipment(commonInfoSearchDto);

        LOGGER.info("[getTotalEquipment] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalEquipment);

    }

    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Equipment> createEquipment(@RequestBody EquipmentDto equipmentDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[equipmentDto]  : {}", equipmentDto);
        Equipment insertEquipment = equipmentService.saveEquipment(equipmentDto);
        LOGGER.info("[createEquipment] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(insertEquipment);
    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Equipment> updateEquipment(@RequestBody EquipmentDto equipmentDto)
            throws Exception{

        Equipment updateEquipment = equipmentService.updateEquipment(equipmentDto);
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[equipmentDto]  : {}", equipmentDto);

        LOGGER.info("[updateEquipment] response Time : {}ms", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(updateEquipment);
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteEquipment(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        equipmentService.deleteEquipment(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadUser(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        equipmentService.excelUploadEquipment(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
