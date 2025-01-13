package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.bookmarkEstimate.BookmarkEstimateDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.entity.BookmarkEstimate;

import java.util.List;
import java.util.Map;


public interface BookmarkEstimateDAO {


    List<BookmarkEstimate> selectBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto);
    List<BookmarkEstimate> selectTotalBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto);


     CommonResultDto insertBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto)  throws Exception;

    CommonResultDto updateBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto) throws Exception;


    String deleteBookmarkEstimate(List<Long> uid) throws Exception;
    String excelUploadBookmarkEstimate(List<Map<String, Object>> requestList) throws Exception;



}
