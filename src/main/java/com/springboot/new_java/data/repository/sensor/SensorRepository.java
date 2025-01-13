package com.springboot.new_java.data.repository.sensor;

import com.springboot.new_java.data.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("sensorRepositorySupport")
public interface SensorRepository extends JpaRepository<Sensor,Long>, SensorRepositoryCustom {

    Sensor findByUid(Long uid);


}
