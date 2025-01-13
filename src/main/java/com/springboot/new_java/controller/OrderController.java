package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.order.OrderDto;
import com.springboot.new_java.data.entity.Order;
import com.springboot.new_java.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Controller
public class OrderController {
    private final OrderService orderService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Order>> getOrder(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Order> selectedOrder = orderService.getOrder(commonInfoSearchDto);

        LOGGER.info("[getTotalOrderSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedOrder);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<Order>> getTotalOrder(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Order> selectedTotalOrder = orderService.getTotalOrder(commonSearchDto);

        LOGGER.info("[getTotalOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalOrder);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createOrder(@RequestBody OrderDto orderDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[orderDto]  : {}", orderDto);
        CommonResultDto orderResultDto = orderService.saveOrder(orderDto);
        LOGGER.info("[createOrder] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  orderResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateOrder(@RequestBody OrderDto orderDto)
            throws Exception{
        CommonResultDto orderResultDto = orderService.updateOrder(orderDto);
        return orderResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        orderService.deleteOrder(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
