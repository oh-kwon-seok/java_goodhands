package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dao.SensorDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.sensor.SensorDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.sensor.SensorRepository;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SensorDAOImpl implements SensorDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SensorDAOImpl.class);

    private final SensorRepository sensorRepository;


    @Autowired
    public SensorDAOImpl(SensorRepository sensorRepository

                      ) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Sensor insertSensor(SensorDto sensorDto) throws Exception {


        Sensor  sensor = new Sensor();


        sensor.setCode(sensorDto.getCode());
        sensor.setData(sensorDto.getData());
        sensor.setType(sensorDto.getType());
        sensor.setCreated(LocalDateTime.now());

        Sensor insertSensor = sensorRepository.save(sensor);

        return insertSensor;

    }

    @Override
    public List<Sensor> selectTotalSensor(CommonSearchDto commonSearchDto) {
        return sensorRepository.findAll(commonSearchDto);

    }






}