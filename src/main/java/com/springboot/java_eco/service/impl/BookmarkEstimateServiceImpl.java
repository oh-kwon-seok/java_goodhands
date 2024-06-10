package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.BookmarkEstimateDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.bookmarkEstimate.BookmarkEstimateDto;
import com.springboot.java_eco.data.entity.BookmarkEstimate;
import com.springboot.java_eco.service.BookmarkEstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookmarkEstimateServiceImpl implements BookmarkEstimateService {
    private final BookmarkEstimateDAO bookmarkEstimateDAO;

    @Autowired
    public BookmarkEstimateServiceImpl(@Qualifier("bookmarkEstimateDAOImpl") BookmarkEstimateDAO bookmarkEstimateDAO){
        this.bookmarkEstimateDAO = bookmarkEstimateDAO;
    }

    @Override
    public List<BookmarkEstimate> getBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto){
        return bookmarkEstimateDAO.selectBookmarkEstimate(commonInfoSearchDto);
    }
    @Override
    public List<BookmarkEstimate> getTotalBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto){
        return bookmarkEstimateDAO.selectTotalBookmarkEstimate(commonInfoSearchDto);
    }
    @Override
    public CommonResultDto saveBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto) throws Exception {

        return bookmarkEstimateDAO.insertBookmarkEstimate(bookmarkEstimateDto);

    }
    @Override
    public CommonResultDto updateBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto) throws Exception {
        return bookmarkEstimateDAO.updateBookmarkEstimate(bookmarkEstimateDto);
    }
    @Override
    public void deleteBookmarkEstimate(List<Long> uid) throws Exception {
        bookmarkEstimateDAO.deleteBookmarkEstimate(uid);
    }
    @Override
    public void excelUploadBookmarkEstimate(List<Map<String, Object>> requestList) throws Exception {
        bookmarkEstimateDAO.excelUploadBookmarkEstimate(requestList);
    }

}
