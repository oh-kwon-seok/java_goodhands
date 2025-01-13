package com.springboot.new_java.data.repository.bookmarkEstimateSub;

import com.springboot.new_java.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;

import com.springboot.new_java.data.entity.BookmarkEstimateSub;

import java.util.List;

public interface BookmarkEstimateSubRepositoryCustom {


    List<BookmarkEstimateSub> findAll(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);
    List<BookmarkEstimateSub> findInfo(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);


    List<BookmarkEstimateSub> findByBookmarkEstimateUidSelect(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto);


}
