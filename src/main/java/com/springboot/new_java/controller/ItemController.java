package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.item.ItemDto;
import com.springboot.new_java.data.entity.Item;
import com.springboot.new_java.service.ItemService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
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
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filePath") String filePath) {
        try {
            File file = new File(filePath);

            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Resource resource = new UrlResource(file.toURI());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            String filename = file.getName();
            String encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping(value= "/save", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<Item> createItem(
            @RequestPart("itemDto") ItemDto itemDto, // JSON 데이터 처리
            @RequestPart("file_url") MultipartFile file_url // 파일 처리
    ) throws Exception {
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[itemDto]  : {}", itemDto);
        Item insertItem = itemService.saveItem(itemDto,file_url);
        LOGGER.info("[createItem] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(insertItem);
    }
    @PostMapping(value= "/update", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<Item> updateItem(
            @RequestPart("itemDto") ItemDto itemDto, // JSON 데이터 처리

            @RequestPart(value = "file_url", required = false) MultipartFile file_url // 파일 처리
    ) throws Exception {

        Item updateItem = itemService.updateItem(itemDto,file_url);
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
