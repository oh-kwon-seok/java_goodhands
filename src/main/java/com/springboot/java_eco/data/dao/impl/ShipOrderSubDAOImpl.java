package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dao.ShipOrderSubDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.java_eco.data.entity.ShipOrderSub;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.shipOrder.ShipOrderRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;

import com.springboot.java_eco.data.repository.shipOrderSub.ShipOrderSubRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShipOrderSubDAOImpl implements ShipOrderSubDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ShipOrderSubDAOImpl.class);

    private final ShipOrderSubRepository shipOrderSubRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final ShipOrderRepository shipOrderRepository;



    @Autowired
    public ShipOrderSubDAOImpl(ShipOrderSubRepository shipOrderSubRepository,
                               ShipOrderRepository shipOrderRepository,
                               UserRepository userRepository,
                               ItemRepository itemRepository,
                               CompanyRepository companyRepository

                          ) {
        this.shipOrderSubRepository = shipOrderSubRepository;
        this. shipOrderRepository = shipOrderRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<ShipOrderSub> selectShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto) {
        return shipOrderSubRepository.findInfo(shipOrderSubSearchDto);

    }

    @Override
    public List<ShipOrderSub> selectTotalShipOrderSub(ShipOrderSubSearchDto shipOrderSubSearchDto) {
        return shipOrderSubRepository.findAll(shipOrderSubSearchDto);

    }
    @Override
    public List<ShipOrderSub> selectShipOrderUidSelect(ShipOrderSubSearchDto shipOrderSubSearchDto) {
        return shipOrderSubRepository.findByShipOrderUidSelect(shipOrderSubSearchDto);

    }



}