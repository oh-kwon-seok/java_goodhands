package com.springboot.new_java.data.repository.bookmarkEstimate;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.BookmarkEstimate;

import java.util.List;

public interface BookmarkEstimateRepositoryCustom {


    List<BookmarkEstimate> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<BookmarkEstimate> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
