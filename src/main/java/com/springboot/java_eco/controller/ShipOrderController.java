package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrder.ShipOrderDto;
import com.springboot.java_eco.data.entity.ShipOrder;
import com.springboot.java_eco.service.ShipOrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ship_order")
@Controller
public class ShipOrderController {
    private final ShipOrderService shipOrderService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ShipOrderController.class);

    @Autowired
    public ShipOrderController(ShipOrderService shipOrderService){
        this.shipOrderService = shipOrderService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<ShipOrder>> getShipOrder(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<ShipOrder> selectedShipOrder = shipOrderService.getShipOrder(commonInfoSearchDto);

        LOGGER.info("[getTotalShipOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedShipOrder);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<ShipOrder>> getTotalShipOrder(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<ShipOrder> selectedTotalShipOrder = shipOrderService.getTotalShipOrder(commonSearchDto);

        LOGGER.info("[getTotalShipOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalShipOrder);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createShipOrder(@RequestBody ShipOrderDto shipOrderDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[shipOrderDto]  : {}", shipOrderDto);
        CommonResultDto shipOrderResultDto = shipOrderService.saveShipOrder(shipOrderDto);
        LOGGER.info("[createShipOrder] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  shipOrderResultDto;

    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateShipOrder(@RequestBody ShipOrderDto shipOrderDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[shipOrderDto]  : {}", shipOrderDto);
        CommonResultDto shipOrderResultDto = shipOrderService.updateShipOrder(shipOrderDto);
        LOGGER.info("[updateShipOrder] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  shipOrderResultDto;

    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteShipOrder(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        shipOrderService.deleteShipOrder(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
