package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.ShipOrderDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrder.ShipOrderDto;
import com.springboot.java_eco.data.entity.ShipOrder;
import com.springboot.java_eco.service.ShipOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipOrderServiceImpl implements ShipOrderService {
    private final ShipOrderDAO shipOrderDAO;

    @Autowired
    public ShipOrderServiceImpl(@Qualifier("shipOrderDAOImpl") ShipOrderDAO shipOrderDAO){
        this.shipOrderDAO = shipOrderDAO;
    }

    @Override
    public List<ShipOrder> getShipOrder(CommonInfoSearchDto commonInfoSearchDto){
        return shipOrderDAO.selectShipOrder(commonInfoSearchDto);
    }
    @Override
    public List<ShipOrder> getTotalShipOrder(CommonSearchDto commonSearchDto){
        return shipOrderDAO.selectTotalShipOrder(commonSearchDto);
    }
    @Override
    public CommonResultDto saveShipOrder(ShipOrderDto workPlanDto) throws Exception {

        return shipOrderDAO.insertShipOrder(workPlanDto);

    }
    @Override
    public CommonResultDto updateShipOrder(ShipOrderDto workPlanDto) throws Exception {

        return shipOrderDAO.updateShipOrder(workPlanDto);

    }



    @Override
    public void deleteShipOrder(List<Long> uid) throws Exception {
        shipOrderDAO.deleteShipOrder(uid);
    }


}
