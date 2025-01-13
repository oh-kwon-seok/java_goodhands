package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.bookmarkEstimate.BookmarkEstimateDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;

import com.springboot.new_java.data.entity.BookmarkEstimate;
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
