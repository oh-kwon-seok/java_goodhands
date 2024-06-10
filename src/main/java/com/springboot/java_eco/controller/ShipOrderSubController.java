package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrderSub.ShipOrderSubSearchDto;
import com.springboot.java_eco.data.entity.ShipOrderSub;
import com.springboot.java_eco.service.ShipOrderSubService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ship_order_sub")
@Controller
public class ShipOrderSubController {
    private final ShipOrderSubService shipOrderSubService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ShipOrderSubController.class);

    @Autowired
    public ShipOrderSubController(ShipOrderSubService shipOrderSubService){
        this.shipOrderSubService = shipOrderSubService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<ShipOrderSub>> getShipOrderSub(@ModelAttribute ShipOrderSubSearchDto shipOrderSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<ShipOrderSub> selectedShipOrderSub = shipOrderSubService.getShipOrderSub(shipOrderSubSearchDto);

        LOGGER.info("[getTotalShipOrderSubSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedShipOrderSub);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<ShipOrderSub>> getTotalShipOrderSub(@ModelAttribute ShipOrderSubSearchDto shipOrderSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<ShipOrderSub> selectedTotalShipOrderSub = shipOrderSubService.getTotalShipOrderSub(shipOrderSubSearchDto);

        LOGGER.info("[getTotalShipOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalShipOrderSub);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<ShipOrderSub>> getWorkTaskUidSelect(@ModelAttribute ShipOrderSubSearchDto shipOrderSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<ShipOrderSub> selectWorkTaskUidSelect = shipOrderSubService.getShipOrderUidSelect(shipOrderSubSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectWorkTaskUidSelect);

    }







}
