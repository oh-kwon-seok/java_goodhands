package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.sensor.SensorDto;
import com.springboot.new_java.data.entity.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorService {

    List<Sensor> getTotalSensor(CommonSearchDto commonSearchDto);
    
    Sensor saveSensor(SensorDto sensorDto) throws Exception;

    
}
