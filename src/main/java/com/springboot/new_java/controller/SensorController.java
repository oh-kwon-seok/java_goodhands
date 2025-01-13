package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.sensor.SensorDto;
import com.springboot.new_java.data.entity.Sensor;
import com.springboot.new_java.service.SensorService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@Controller
public class SensorController {
    private final SensorService sensorService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SensorController.class);

    @Autowired
    public SensorController(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<Sensor>> getTotalSensor(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Sensor> selectedTotalSensor = sensorService.getTotalSensor(commonSearchDto);

        LOGGER.info("[getTotalSensor] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalSensor);

    }
    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Sensor> createSensor(@RequestBody SensorDto sensorDto, HttpServletRequest request
    ) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[sensorDto]  : {}", sensorDto);
        Sensor sensor = sensorService.saveSensor(sensorDto);
        LOGGER.info("[createSensor] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(sensor);
    }
}
