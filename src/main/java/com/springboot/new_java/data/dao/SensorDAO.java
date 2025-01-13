package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.sensor.SensorDto;
import com.springboot.new_java.data.entity.Sensor;

import java.util.List;


public interface SensorDAO {



    Sensor insertSensor(SensorDto sensorDto) throws Exception;


    List<Sensor> selectTotalSensor(CommonSearchDto commonSearchDto);




}
