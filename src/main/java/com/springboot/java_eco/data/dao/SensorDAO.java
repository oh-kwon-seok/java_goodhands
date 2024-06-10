package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.sensor.SensorDto;
import com.springboot.java_eco.data.entity.Sensor;

import java.util.List;


public interface SensorDAO {



    Sensor insertSensor(SensorDto sensorDto) throws Exception;


    List<Sensor> selectTotalSensor(CommonSearchDto commonSearchDto);




}
