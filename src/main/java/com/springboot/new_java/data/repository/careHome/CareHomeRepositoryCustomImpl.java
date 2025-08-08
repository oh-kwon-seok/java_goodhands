package com.springboot.new_java.data.repository.careHome;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.care.CareHome;
import com.springboot.new_java.data.entity.care.QCareHome;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CareHomeRepositoryCustomImpl extends QuerydslRepositorySupport implements CareHomeRepositoryCustom {

    public CareHomeRepositoryCustomImpl(){
        super(CareHome.class);
    }

    @Override
    public List<CareHome> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QCareHome careHome = QCareHome.careHome;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (careHome.name != null) {
                builder.or(careHome.name.like("%" + search_text + "%"));
            }
            if (careHome.careHomeType.name != null) {
                builder.or(careHome.careHomeType.name.like("%" + search_text + "%"));
            }

        }else {
            if("name".equals(filter_title)){
                builder.and(careHome.name.like("%" + search_text + "%"));
            }
            else if("careHomeType".equals(filter_title)){
                builder.and(careHome.careHomeType.name.like("%" + search_text + "%"));
            }
        }
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = careHome.used.eq(true);
        Predicate predicate = builder.getValue();


        List<CareHome> careHomeList = from(careHome)
                .select(careHome)
                .where(predicate,used)
                .fetch();

        return careHomeList;
    }
    @Override
    public List<CareHome> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QCareHome careHome = QCareHome.careHome;

        Predicate used = careHome.used.eq(true);

        List<CareHome> careHomeList = from(careHome)
                .select(careHome)
                .where(used)
                .fetch();

        return careHomeList;
    }




}
