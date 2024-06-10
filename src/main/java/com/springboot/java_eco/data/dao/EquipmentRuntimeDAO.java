package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.equipmentRuntime.EquipmentRuntimeDto;
import com.springboot.java_eco.data.entity.EquipmentRuntime;

import java.util.List;


public interface EquipmentRuntimeDAO {


    List<EquipmentRuntime> selectEquipmentRuntime(CommonInfoSearchDto commonInfoSearchDto);
    List<EquipmentRuntime> selectTotalEquipmentRuntime(CommonSearchDto commonSearchDto);


     CommonResultDto insertEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto)  throws Exception;

    CommonResultDto updateEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto) throws Exception;


    String deleteEquipmentRuntime(List<Long> uid) throws Exception;



}
