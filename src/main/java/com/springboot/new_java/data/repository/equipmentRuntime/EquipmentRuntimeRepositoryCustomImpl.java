package com.springboot.new_java.data.repository.equipmentRuntime;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.EquipmentRuntimeController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.EquipmentRuntime;
import com.springboot.new_java.data.entity.QEquipmentRuntime;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EquipmentRuntimeRepositoryCustomImpl extends QuerydslRepositorySupport implements EquipmentRuntimeRepositoryCustom {

    public EquipmentRuntimeRepositoryCustomImpl(){
        super(EquipmentRuntime.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EquipmentRuntimeController.class);

    
    @Override
    public List<EquipmentRuntime> findAll(CommonSearchDto commonSearchDto){
        QEquipmentRuntime equipmentRuntime = QEquipmentRuntime.equipmentRuntime;

        String filter_title = commonSearchDto.getFilter_title();
        String search_text = commonSearchDto.getSearch_text();
        LocalDateTime start_date = commonSearchDto.getStart_date();
        LocalDateTime end_date = commonSearchDto.getEnd_date();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (equipmentRuntime.equipment.name != null) {
                builder.or(equipmentRuntime.equipment.name.like("%" + search_text + "%"));
            }



        }else {
            if("name".equals(filter_title)){
                builder.and(equipmentRuntime.equipment.name.like("%" + search_text + "%"));
            }


        }

        Predicate dateRange = equipmentRuntime.created.between(start_date, end_date);
        Predicate predicate = builder.getValue();
        Predicate used = equipmentRuntime.used.eq(1L);
        List<EquipmentRuntime> equipmentRuntimeList = from(equipmentRuntime)
                .select(equipmentRuntime)
                .where(predicate,dateRange,used)
                .orderBy(equipmentRuntime.created.desc()) // Order by created field in descending order
                .fetch();



        return equipmentRuntimeList;

    }

    @Override
    public List<EquipmentRuntime> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QEquipmentRuntime equipmentRuntime = QEquipmentRuntime.equipmentRuntime;

        Predicate used = equipmentRuntime.used.eq(1L);

        List<EquipmentRuntime> equipmentRuntimeList = from(equipmentRuntime)
                .select(equipmentRuntime)
                .where(used)
                .fetch();

        return equipmentRuntimeList;

    }
}
