package com.springboot.java_eco.data.repository.sensor;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.Sensor;
import com.springboot.java_eco.data.entity.QSensor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SensorRepositoryCustomImpl extends QuerydslRepositorySupport implements SensorRepositoryCustom {

    public SensorRepositoryCustomImpl(){
        super(Sensor.class);
    }

    @Override
    public List<Sensor> findAll(CommonSearchDto commonSearchDto){
        QSensor sensor = QSensor.sensor;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();

        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (sensor.code != null) {
                builder.or(sensor.code.like("%" + search_text + "%"));
            }
            if (sensor.type != null) {
                builder.or(sensor.type.like("%" + search_text + "%"));
            }
        }else {
            if("code".equals(filter_title)){
                builder.and(sensor.code.like("%" + search_text + "%"));
            }else if("temp".equals(filter_title)){
                builder.and(sensor.type.eq("TEMP"));
            }
            else if("humi".equals(filter_title)){
                builder.and(sensor.type.eq("HUMI"));
            }
            else if("ph".equals(filter_title)){
                builder.and(sensor.type.eq("PH"));
            }
            else if("weight".equals(filter_title)){
                builder.and(sensor.type.eq("WEIGHT"));
            }
        }
        Predicate dateRange = sensor.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();

        List<Sensor> sensorList = from(sensor)
                .select(sensor)
                .where(predicate,dateRange)
                .orderBy(sensor.updated.desc())
                .fetch();

        return sensorList;
    }





}
