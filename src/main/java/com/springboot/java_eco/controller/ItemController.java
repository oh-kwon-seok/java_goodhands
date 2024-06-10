package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.item.ItemDto;
import com.springboot.java_eco.data.entity.Item;
import com.springboot.java_eco.service.ItemService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
@Controller
public class ItemController {
    private final ItemService itemService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ItemController.class);

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping(value= "/select")
    public ResponseEntity<List<Item>> getTotalItem(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Item> selectedTotalItem = itemService.getTotalItem(commonInfoSearchDto);

        LOGGER.info("[getTotalItem] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalItem);

    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<Item>> getItem(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Item> selectedTotalItem = itemService.getItem(commonInfoSearchDto);

        LOGGER.info("[getItem] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalItem);

    }
    @GetMapping(value= "/material_select")
    public ResponseEntity<List<Item>> getMaterial(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<Item> selectedMaterial = itemService.getMaterial(commonInfoSearchDto);

        LOGGER.info("[getItem] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedMaterial);

    }


    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Item> createItem(@RequestBody ItemDto itemDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[itemDto]  : {}", itemDto);
        Item insertItem = itemService.saveItem(itemDto);
        LOGGER.info("[createItem] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(insertItem);
    }
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Item> updateItem(@RequestBody ItemDto itemDto)
            throws Exception{

        Item updateItem = itemService.updateItem(itemDto);
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[itemDto]  : {}", itemDto);

        LOGGER.info("[updateItem] response Time : {}ms", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(updateItem);
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteItem(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        itemService.deleteItem(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadItem(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        itemService.excelUploadItem(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
