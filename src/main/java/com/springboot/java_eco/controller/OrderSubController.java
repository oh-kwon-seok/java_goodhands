package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.orderSub.OrderSubSearchDto;
import com.springboot.java_eco.data.entity.OrderSub;
import com.springboot.java_eco.service.OrderSubService;
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
@RequestMapping("/order_sub")
@Controller
public class OrderSubController {
    private final OrderSubService orderSubService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderSubController.class);

    @Autowired
    public OrderSubController(OrderSubService orderSubService){
        this.orderSubService = orderSubService;
    }

    @GetMapping(value= "/select") // 매입처별/품목별 매입,  품목별/상품별 매입단가 조회
    public ResponseEntity<List<OrderSub>> getTotalOrderSub(@ModelAttribute OrderSubSearchDto orderSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();
        List<OrderSub> selectedTotalOrderSub = orderSubService.getTotalOrderSub(orderSubSearchDto);

        LOGGER.info("[getTotalUserOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalOrderSub);
    }



    @GetMapping(value= "/info_select")
    public ResponseEntity<List<OrderSub>> getOrderSub(@ModelAttribute OrderSubSearchDto orderSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<OrderSub> selectedTotalOrderSub = orderSubService.getOrderSub(orderSubSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalOrderSub);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<OrderSub>> getOrderUidSelect(@ModelAttribute OrderSubSearchDto orderSubSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<OrderSub> selectOrderUidSelect = orderSubService.getOrderUidSelect(orderSubSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectOrderUidSelect);

    }


}
