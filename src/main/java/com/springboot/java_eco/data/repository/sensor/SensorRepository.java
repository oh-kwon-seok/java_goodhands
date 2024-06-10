package com.springboot.java_eco.data.repository.sensor;

import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("sensorRepositorySupport")
public interface SensorRepository extends JpaRepository<Sensor,Long>, SensorRepositoryCustom {

    Sensor findByUid(Long uid);


}
