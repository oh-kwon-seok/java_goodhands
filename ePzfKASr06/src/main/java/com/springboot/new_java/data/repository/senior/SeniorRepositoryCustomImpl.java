package com.springboot.new_java.data.repository.senior;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.senior.QSenior;
import com.springboot.new_java.data.entity.senior.Senior;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeniorRepositoryCustomImpl extends QuerydslRepositorySupport implements SeniorRepositoryCustom {

    public SeniorRepositoryCustomImpl(){
        super(Senior.class);
    }

    @Override
    public List<Senior> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QSenior senior = QSenior.senior;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (senior.name != null) {
                builder.or(senior.name.like("%" + search_text + "%"));
            }


        }else {
            if("name".equals(filter_title)){
                builder.and(senior.name.like("%" + search_text + "%"));
            }

        }
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = senior.used.eq(true);
        Predicate predicate = builder.getValue();


        List<Senior> seniorList = from(senior)
                .select(senior)
                .where(predicate,used)
                .fetch();

        return seniorList;
    }
    @Override
    public List<Senior> findInfo(){

        QSenior senior = QSenior.senior;

        Predicate used = senior.used.eq(true);

        List<Senior> seniorList = from(senior)
                .select(senior)
                .where(used)
                .fetch();

        return seniorList;
    }




}
