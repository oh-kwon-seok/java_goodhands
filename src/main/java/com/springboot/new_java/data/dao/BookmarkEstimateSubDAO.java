package com.springboot.new_java.data.dao;



import com.springboot.new_java.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;

import com.springboot.new_java.data.entity.BookmarkEstimateSub;

import java.util.List;
import java.util.Map;


public interface BookmarkEstimateSubDAO {


    List<BookmarkEstimateSub> selectTotalBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);


    List<BookmarkEstimateSub> selectBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);

    List<BookmarkEstimateSub> selectBookmarkEstimateUidSelect(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);

    String excelUploadBookmarkEstimateSub(List<Map<String, Object>> requestList) throws Exception;

}
