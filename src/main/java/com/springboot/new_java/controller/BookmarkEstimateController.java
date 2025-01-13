package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.bookmarkEstimate.BookmarkEstimateDto;
import com.springboot.new_java.data.entity.BookmarkEstimate;
import com.springboot.new_java.service.BookmarkEstimateService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookmark_estimate")
@Controller
public class BookmarkEstimateController {
    private final BookmarkEstimateService bookmarkEstimateService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookmarkEstimateController.class);

    @Autowired
    public BookmarkEstimateController(BookmarkEstimateService bookmarkEstimateService){
        this.bookmarkEstimateService = bookmarkEstimateService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<BookmarkEstimate>> getBookmarkEstimate(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<BookmarkEstimate> selectedBookmarkEstimate = bookmarkEstimateService.getBookmarkEstimate(commonInfoSearchDto);

        LOGGER.info("[getTotalBookmarkEstimateSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedBookmarkEstimate);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<BookmarkEstimate>> getTotalBookmarkEstimate(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<BookmarkEstimate> selectedTotalBookmarkEstimate = bookmarkEstimateService.getTotalBookmarkEstimate(commonInfoSearchDto);

        LOGGER.info("[getTotalBookmarkEstimate] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalBookmarkEstimate);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createBookmarkEstimate(@RequestBody BookmarkEstimateDto bookmarkEstimateDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[bookmarkEstimateDto]  : {}", bookmarkEstimateDto);
        CommonResultDto bookmarkEstimateResultDto = bookmarkEstimateService.saveBookmarkEstimate(bookmarkEstimateDto);
        LOGGER.info("[createBookmarkEstimate] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  bookmarkEstimateResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateBookmarkEstimate(@RequestBody BookmarkEstimateDto bookmarkEstimateDto)
            throws Exception{


        CommonResultDto bookmarkEstimateResultDto = bookmarkEstimateService.updateBookmarkEstimate(bookmarkEstimateDto);
        return bookmarkEstimateResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        bookmarkEstimateService.deleteBookmarkEstimate(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadBookmarkEstimate(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        bookmarkEstimateService.excelUploadBookmarkEstimate(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 업로드되었습니다.");
    }

}
