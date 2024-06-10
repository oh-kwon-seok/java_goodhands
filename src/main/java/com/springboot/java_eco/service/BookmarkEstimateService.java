package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.bookmarkEstimate.BookmarkEstimateDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;

import com.springboot.java_eco.data.entity.BookmarkEstimate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookmarkEstimateService {

    List<BookmarkEstimate> getBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto);

    List<BookmarkEstimate> getTotalBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto) throws Exception;

    CommonResultDto updateBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto) throws Exception;

    void deleteBookmarkEstimate(List<Long> uid) throws Exception;

    void excelUploadBookmarkEstimate(List<Map<String, Object>> requestList) throws Exception;

}
