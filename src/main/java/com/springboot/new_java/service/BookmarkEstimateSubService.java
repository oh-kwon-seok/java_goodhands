package com.springboot.new_java.service;



import com.springboot.new_java.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;

import com.springboot.new_java.data.entity.BookmarkEstimateSub;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookmarkEstimateSubService {

    List<BookmarkEstimateSub> getTotalBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);

    List<BookmarkEstimateSub> getBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);

    List<BookmarkEstimateSub> getBookmarkEstimateUidSelect(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);


    void excelUploadBookmarkEstimateSub(List<Map<String, Object>> requestList) throws Exception;


}
