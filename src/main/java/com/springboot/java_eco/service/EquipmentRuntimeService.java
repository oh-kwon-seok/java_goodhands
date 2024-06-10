package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.equipmentRuntime.EquipmentRuntimeDto;
import com.springboot.java_eco.data.entity.EquipmentRuntime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentRuntimeService {

    List<EquipmentRuntime> getEquipmentRuntime(CommonInfoSearchDto commonInfoSearchDto);

    List<EquipmentRuntime> getTotalEquipmentRuntime(CommonSearchDto commonSearchDto);


    CommonResultDto saveEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto) throws Exception;

    CommonResultDto updateEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto) throws Exception;

    void deleteEquipmentRuntime(List<Long> uid) throws Exception;


}
