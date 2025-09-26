package com.springboot.new_java.data.repository.careHomeType;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.care.CareHomeType;

import com.springboot.new_java.data.entity.care.QCareHomeType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CareHomeTypeRepositoryCustomImpl extends QuerydslRepositorySupport implements CareHomeTypeRepositoryCustom {

    public CareHomeTypeRepositoryCustomImpl(){
        super(CareHomeType.class);
    }

    @Override
    public List<CareHomeType> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QCareHomeType careHomeType = QCareHomeType.careHomeType;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (careHomeType.name != null) {
                builder.or(careHomeType.name.like("%" + search_text + "%"));
            }


        }else {
            if("name".equals(filter_title)){
                builder.and(careHomeType.name.like("%" + search_text + "%"));
            }

        }
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = careHomeType.used.eq(true);
        Predicate predicate = builder.getValue();


        List<CareHomeType> careHomeTypeList = from(careHomeType)
                .select(careHomeType)
                .where(predicate,used)
                .orderBy(careHomeType.name.desc())
                .fetch();

        return careHomeTypeList;
    }
    @Override
    public List<CareHomeType> findInfo(){

        QCareHomeType careHomeType = QCareHomeType.careHomeType;

        Predicate used = careHomeType.used.eq(true);

        List<CareHomeType> careHomeTypeList = from(careHomeType)
                .select(careHomeType)
                .where(used)
                .fetch();

        return careHomeTypeList;
    }




}
