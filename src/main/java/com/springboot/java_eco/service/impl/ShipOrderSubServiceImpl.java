package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.ShipOrderSubDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.java_eco.data.entity.ShipOrderSub;
import com.springboot.java_eco.service.ShipOrderSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipOrderSubServiceImpl implements ShipOrderSubService {
    private final ShipOrderSubDAO shipOrderSubDAO;

    @Autowired
    public ShipOrderSubServiceImpl(@Qualifier("shipOrderSubDAOImpl") ShipOrderSubDAO shipOrderSubDAO){
        this.shipOrderSubDAO = shipOrderSubDAO;
    }

    @Override
    public List<ShipOrderSub> getShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto){
        return shipOrderSubDAO.selectShipOrderSub(shipOrderSubSearchDto);
    }
    @Override
    public List<ShipOrderSub> getTotalShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto){
        return shipOrderSubDAO.selectTotalShipOrderSub(shipOrderSubSearchDto);
    }

    @Override
    public List<ShipOrderSub> getShipOrderUidSelect(ShipOrderSubSearchDto shipOrderSubSearchDto){
        return shipOrderSubDAO.selectShipOrderUidSelect(shipOrderSubSearchDto);
    }



}
