package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;

import com.springboot.java_eco.data.entity.BookmarkEstimateSub;
import com.springboot.java_eco.service.BookmarkEstimateSubService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookmark_estimate_sub")
@Controller
public class BookmarkEstimateSubController {
    private final BookmarkEstimateSubService bookmarkEstimateSubService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookmarkEstimateSubController.class);

    @Autowired
    public BookmarkEstimateSubController(BookmarkEstimateSubService bookmarkEstimateSubService){
        this.bookmarkEstimateSubService = bookmarkEstimateSubService;
    }

    @GetMapping(value= "/select") // 매입처별/품목별 매입,  품목별/상품별 매입단가 조회
    public ResponseEntity<List<BookmarkEstimateSub>> getTotalBookmarkEstimateSub(@ModelAttribute BookmarkEstimateSubSearchDto bookmarkEstimateSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();
        List<BookmarkEstimateSub> selectedTotalBookmarkEstimateSub = bookmarkEstimateSubService.getTotalBookmarkEstimateSub(bookmarkEstimateSearchDto);

        LOGGER.info("[getTotalUserOrder] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalBookmarkEstimateSub);
    }



    @GetMapping(value= "/info_select")
    public ResponseEntity<List<BookmarkEstimateSub>> getBookmarkEstimateSub(@ModelAttribute BookmarkEstimateSubSearchDto bookmarkEstimateSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<BookmarkEstimateSub> selectedTotalBookmarkEstimateSub = bookmarkEstimateSubService.getBookmarkEstimateSub(bookmarkEstimateSearchDto);

        LOGGER.info("[getTotalBookmarkEstimateSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalBookmarkEstimateSub);

    }
    @GetMapping(value= "/uid_select")
    public ResponseEntity<List<BookmarkEstimateSub>> getBookmarkEstimateUidSelect(@ModelAttribute BookmarkEstimateSubSearchDto bookmarkEstimateSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<BookmarkEstimateSub> selectBookmarkEstimateUidSelect = bookmarkEstimateSubService.getBookmarkEstimateUidSelect(bookmarkEstimateSearchDto);

        LOGGER.info("[getTotalBookmarkEstimateSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectBookmarkEstimateUidSelect);

    }

    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadBookmarkEstimateSub(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        bookmarkEstimateSubService.excelUploadBookmarkEstimateSub(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 업로드되었습니다.");
    }
}
