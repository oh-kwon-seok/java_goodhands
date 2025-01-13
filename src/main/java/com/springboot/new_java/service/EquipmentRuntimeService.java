package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.equipmentRuntime.EquipmentRuntimeDto;
import com.springboot.new_java.data.entity.EquipmentRuntime;
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
