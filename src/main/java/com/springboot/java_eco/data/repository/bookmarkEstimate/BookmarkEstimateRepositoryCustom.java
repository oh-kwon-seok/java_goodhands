package com.springboot.java_eco.data.repository.bookmarkEstimate;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.BookmarkEstimate;

import java.util.List;

public interface BookmarkEstimateRepositoryCustom {


    List<BookmarkEstimate> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<BookmarkEstimate> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
