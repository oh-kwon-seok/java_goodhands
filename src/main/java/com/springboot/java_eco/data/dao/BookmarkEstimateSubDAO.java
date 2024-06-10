package com.springboot.java_eco.data.dao;



import com.springboot.java_eco.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;

import com.springboot.java_eco.data.entity.BookmarkEstimateSub;

import java.util.List;
import java.util.Map;


public interface BookmarkEstimateSubDAO {


    List<BookmarkEstimateSub> selectTotalBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);


    List<BookmarkEstimateSub> selectBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);

    List<BookmarkEstimateSub> selectBookmarkEstimateUidSelect(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);

    String excelUploadBookmarkEstimateSub(List<Map<String, Object>> requestList) throws Exception;

}
