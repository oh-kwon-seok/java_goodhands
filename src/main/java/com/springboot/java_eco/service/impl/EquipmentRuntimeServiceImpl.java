package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.EquipmentRuntimeDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.equipmentRuntime.EquipmentRuntimeDto;
import com.springboot.java_eco.data.entity.EquipmentRuntime;
import com.springboot.java_eco.service.EquipmentRuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentRuntimeServiceImpl implements EquipmentRuntimeService {
    private final EquipmentRuntimeDAO equipmentRuntimeDAO;

    @Autowired
    public EquipmentRuntimeServiceImpl(@Qualifier("equipmentRuntimeDAOImpl") EquipmentRuntimeDAO equipmentRuntimeDAO){
        this.equipmentRuntimeDAO = equipmentRuntimeDAO;
    }

    @Override
    public List<EquipmentRuntime> getEquipmentRuntime(CommonInfoSearchDto commonInfoSearchDto){
        return equipmentRuntimeDAO.selectEquipmentRuntime(commonInfoSearchDto);
    }
    @Override
    public List<EquipmentRuntime> getTotalEquipmentRuntime(CommonSearchDto commonSearchDto){
        return equipmentRuntimeDAO.selectTotalEquipmentRuntime(commonSearchDto);
    }
    @Override
    public CommonResultDto saveEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto) throws Exception {

        return equipmentRuntimeDAO.insertEquipmentRuntime(equipmentRuntimeDto);

    }
    @Override
    public CommonResultDto updateEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto) throws Exception {
        return equipmentRuntimeDAO.updateEquipmentRuntime(equipmentRuntimeDto);
    }
    @Override
    public void deleteEquipmentRuntime(List<Long> uid) throws Exception {
        equipmentRuntimeDAO.deleteEquipmentRuntime(uid);
    }


}
