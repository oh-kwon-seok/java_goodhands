package com.springboot.java_eco.data.repository.sensor;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.Sensor;

import java.util.List;

public interface SensorRepositoryCustom {
    List<Sensor> findAll(CommonSearchDto sensorSearchDto);


}
