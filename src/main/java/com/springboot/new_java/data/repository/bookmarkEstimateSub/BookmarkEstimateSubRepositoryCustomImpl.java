package com.springboot.new_java.data.repository.bookmarkEstimateSub;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.BookmarkEstimateSubController;
import com.springboot.new_java.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;
import com.springboot.new_java.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookmarkEstimateSubRepositoryCustomImpl extends QuerydslRepositorySupport implements BookmarkEstimateSubRepositoryCustom {

    public BookmarkEstimateSubRepositoryCustomImpl(){
        super(BookmarkEstimateSub.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookmarkEstimateSubController.class);

    
    @Override
    public List<BookmarkEstimateSub> findAll(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto){
        QBookmarkEstimateSub bookmarkEstimateSub = QBookmarkEstimateSub.bookmarkEstimateSub;
        QItem item = QItem.item;

        String filter_title = bookmarkEstimateSubSearchDto.getFilter_title();
        String search_text = bookmarkEstimateSubSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){


            if (item.code != null) {
                builder.or(item.code.like("%" + search_text + "%"));
            }

        }else {

            if("code".equals(filter_title)){
                builder.and(item.code.like("%" + search_text + "%"));
            }
        }



        Predicate predicate = builder.getValue();

        List<BookmarkEstimateSub> bookmarkEstimateSubList = from(bookmarkEstimateSub)
                .select(bookmarkEstimateSub)
                .where(predicate)
                .orderBy(bookmarkEstimateSub.created.desc()) // Order by created field in descending order
                .fetch();



        return bookmarkEstimateSubList;

    }

    @Override
    public List<BookmarkEstimateSub> findInfo(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto){

        QBookmarkEstimateSub bookmarkEstimateSub = QBookmarkEstimateSub.bookmarkEstimateSub;



        List<BookmarkEstimateSub> bookmarkEstimateSubList = from(bookmarkEstimateSub)
                .select(bookmarkEstimateSub)
                .where()
                .fetch();

        return bookmarkEstimateSubList;

    }

    @Override
    public List<BookmarkEstimateSub> findByBookmarkEstimateUidSelect(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto){
        QItem item = QItem.item;
        QBookmarkEstimate bookmarkEstimate = QBookmarkEstimate.bookmarkEstimate;
        QBookmarkEstimateSub bookmarkEstimateSub = QBookmarkEstimateSub.bookmarkEstimateSub;

        Long search_id = bookmarkEstimateSubSearchDto.getBookmark_estimate_uid();

        Predicate bookmark_estimate_uid = bookmarkEstimate.uid.eq(search_id);

        List<Tuple> results = from(bookmarkEstimateSub)
                .leftJoin(bookmarkEstimateSub.bookmarkEstimate, bookmarkEstimate).fetchJoin()
                .leftJoin(bookmarkEstimateSub.item, item).fetchJoin()

                .select(bookmarkEstimateSub,bookmarkEstimate,item)
                .where(bookmark_estimate_uid)
                .fetch();

        List<BookmarkEstimateSub> bookmarkEstimateSubList = new ArrayList<>();
        for (Tuple result : results) {
            BookmarkEstimateSub bookmarkEstimateSubEntity = result.get(bookmarkEstimateSub);
            bookmarkEstimateSubList.add(bookmarkEstimateSubEntity);
        }
        return bookmarkEstimateSubList;






    }

}
