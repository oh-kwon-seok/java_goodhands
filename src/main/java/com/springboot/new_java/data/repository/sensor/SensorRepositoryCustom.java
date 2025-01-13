package com.springboot.new_java.data.repository.sensor;

import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.Sensor;

import java.util.List;

public interface SensorRepositoryCustom {
    List<Sensor> findAll(CommonSearchDto sensorSearchDto);


}
