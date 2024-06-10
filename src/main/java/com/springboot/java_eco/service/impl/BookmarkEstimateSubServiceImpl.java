package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.BookmarkEstimateSubDAO;
import com.springboot.java_eco.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;
import com.springboot.java_eco.data.entity.BookmarkEstimateSub;
import com.springboot.java_eco.service.BookmarkEstimateSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookmarkEstimateSubServiceImpl implements BookmarkEstimateSubService {
    private final BookmarkEstimateSubDAO bookmarkEstimateSubDAO;

    @Autowired
    public BookmarkEstimateSubServiceImpl(@Qualifier("bookmarkEstimateSubDAOImpl") BookmarkEstimateSubDAO bookmarkEstimateSubDAO){
        this.bookmarkEstimateSubDAO = bookmarkEstimateSubDAO;
    }

    @Override
    public List<BookmarkEstimateSub> getTotalBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto){
        return bookmarkEstimateSubDAO.selectTotalBookmarkEstimateSub(bookmarkEstimateSubSearchDto);
    }

  

    @Override
    public List<BookmarkEstimateSub> getBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto){
        return bookmarkEstimateSubDAO.selectBookmarkEstimateSub(bookmarkEstimateSubSearchDto);
    }


    @Override
    public List<BookmarkEstimateSub> getBookmarkEstimateUidSelect(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto){
        return bookmarkEstimateSubDAO.selectBookmarkEstimateUidSelect(bookmarkEstimateSubSearchDto);
    }

    @Override
    public void excelUploadBookmarkEstimateSub(List<Map<String, Object>> requestList) throws Exception {
        bookmarkEstimateSubDAO.excelUploadBookmarkEstimateSub(requestList);
    }


}
