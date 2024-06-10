package com.springboot.java_eco.data.repository.equipmentRuntime;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.EquipmentRuntime;

import java.util.List;

public interface EquipmentRuntimeRepositoryCustom {


    List<EquipmentRuntime> findAll(CommonSearchDto commonSearchDto);
    List<EquipmentRuntime> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
