package com.springboot.java_eco.data.repository.equipment;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Equipment;

import java.util.List;

public interface EquipmentRepositoryCustom {
    List<Equipment> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Equipment> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
