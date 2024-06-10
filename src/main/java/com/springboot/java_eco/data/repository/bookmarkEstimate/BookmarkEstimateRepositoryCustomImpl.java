package com.springboot.java_eco.data.repository.bookmarkEstimate;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.BookmarkEstimateController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.BookmarkEstimate;
import com.springboot.java_eco.data.entity.QBookmarkEstimate;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookmarkEstimateRepositoryCustomImpl extends QuerydslRepositorySupport implements BookmarkEstimateRepositoryCustom {

    public BookmarkEstimateRepositoryCustomImpl(){
        super(BookmarkEstimate.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookmarkEstimateController.class);

    
    @Override
    public List<BookmarkEstimate> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QBookmarkEstimate bookmarkEstimate = QBookmarkEstimate.bookmarkEstimate;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (bookmarkEstimate.name != null) {
                builder.or(bookmarkEstimate.name.like("%" + search_text + "%"));
            }
            if (bookmarkEstimate.product_spec != null) {
                builder.or(bookmarkEstimate.product_spec.like("%" + search_text + "%"));
            }
            if (bookmarkEstimate.ship_place != null) {
                builder.or(bookmarkEstimate.ship_place.like("%" + search_text + "%"));
            }

            if (bookmarkEstimate.description != null) {
                builder.or(bookmarkEstimate.description.like("%" + search_text + "%"));
            }
            if (bookmarkEstimate.company.name != null) {
                builder.or(bookmarkEstimate.company.name.like("%" + search_text + "%"));
            }


        }else {

            if("name".equals(filter_title)){
                builder.and(bookmarkEstimate.name.like("%" + search_text + "%"));
            }
            else if("product_spec".equals(filter_title)){
                builder.and(bookmarkEstimate.product_spec.like("%" + search_text + "%"));
            }else if("ship_place".equals(filter_title)){
                builder.and(bookmarkEstimate.ship_place.like("%" + search_text + "%"));
            }
            else if("description".equals(filter_title)){
                builder.and(bookmarkEstimate.description.like("%" + search_text + "%"));
            }  else if("company".equals(filter_title)){
                builder.and(bookmarkEstimate.company.name.like("%" + search_text + "%"));
            }

        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = bookmarkEstimate.used.eq(1);



        Predicate predicate = builder.getValue();

        List<BookmarkEstimate> bookmarkEstimateList = from(bookmarkEstimate)
                .select(bookmarkEstimate)
                .where(predicate,used)
                .orderBy(bookmarkEstimate.created.desc()) // Order by created field in descending order
                .fetch();



        return bookmarkEstimateList;

    }

    @Override
    public List<BookmarkEstimate> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QBookmarkEstimate bookmarkEstimate = QBookmarkEstimate.bookmarkEstimate;

        Predicate used = bookmarkEstimate.used.eq(1);

        List<BookmarkEstimate> bookmarkEstimateList = from(bookmarkEstimate)
                .select(bookmarkEstimate)
                .where(used)
                .fetch();

        return bookmarkEstimateList;

    }
}
