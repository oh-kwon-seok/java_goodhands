package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.sensor.SensorDto;
import com.springboot.java_eco.data.entity.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorService {

    List<Sensor> getTotalSensor(CommonSearchDto commonSearchDto);
    
    Sensor saveSensor(SensorDto sensorDto) throws Exception;

    
}
