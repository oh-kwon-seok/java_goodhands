package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.EquipmentDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.equipment.EquipmentDto;
import com.springboot.new_java.data.entity.Equipment;
import com.springboot.new_java.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentDAO equipmentDAO;

    @Autowired
    public EquipmentServiceImpl(@Qualifier("equipmentDAOImpl") EquipmentDAO equipmentDAO){
        this.equipmentDAO = equipmentDAO;
    }


    @Override
    public List<Equipment> getTotalEquipment(CommonInfoSearchDto commonInfoSearchDto){
        return equipmentDAO.selectTotalEquipment(commonInfoSearchDto);
    }

    @Override
    public List<Equipment> getEquipment(CommonInfoSearchDto commonInfoSearchDto){
        return equipmentDAO.selectEquipment(commonInfoSearchDto);
    }
    @Override
    public Equipment saveEquipment(EquipmentDto equipmentDto) throws Exception {

        return equipmentDAO.insertEquipment(equipmentDto);

    }
    @Override
    public Equipment updateEquipment(EquipmentDto equipmentDto) throws Exception {
        return equipmentDAO.updateEquipment(equipmentDto);
    }
    @Override
    public void deleteEquipment(List<Long> uid) throws Exception {
        equipmentDAO.deleteEquipment(uid);
    }
    @Override
    public void excelUploadEquipment(List<Map<String, Object>> requestList) throws Exception {
        equipmentDAO.excelUploadEquipment(requestList);
    }

}
