package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.SensorDAO;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.sensor.SensorDto;
import com.springboot.new_java.data.entity.Sensor;
import com.springboot.new_java.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    private final SensorDAO SensorDAO;

    @Autowired
    public SensorServiceImpl(@Qualifier("sensorDAOImpl") SensorDAO SensorDAO){
        this.SensorDAO = SensorDAO;
    }


    @Override
    public List<Sensor> getTotalSensor(CommonSearchDto commonSearchDto){
        return SensorDAO.selectTotalSensor(commonSearchDto);
    }

    @Override
    public Sensor saveSensor(SensorDto SensorDto) throws Exception {

        return SensorDAO.insertSensor(SensorDto);

    }
}
