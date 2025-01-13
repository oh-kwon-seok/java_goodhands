package com.springboot.new_java.data.repository.type;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Type;
import com.springboot.new_java.data.entity.QType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TypeRepositoryCustomImpl extends QuerydslRepositorySupport implements TypeRepositoryCustom {

    public TypeRepositoryCustomImpl(){
        super(Type.class);
    }

    @Override
    public List<Type> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QType type = QType.type;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();

//        LocalDateTime start_date = commonInfoSearchDto.getStart_date();
//        LocalDateTime end_date = commonInfoSearchDto.getEnd_date();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (type.name != null) {
                builder.or(type.name.like("%" + search_text + "%"));
            }
            if (type.company.name != null) {
                builder.or(type.company.name.like("%" + search_text + "%"));
            }
        }else {
            if("name".equals(filter_title)){
                builder.and(type.name.like("%" + search_text + "%"));
            }else if("company".equals(filter_title)){
                builder.and(type.company.name.like("%" + search_text + "%"));
            }
        }
        //Predicate dateRange = type.created.between(start_date, end_date);
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = type.used.eq(1);

        List<Type> typeList = from(type)
                .select(type)
                .where(used)
                .fetch();

        return typeList;
    }
    @Override
    public List<Type> findInfo(CommonInfoSearchDto TypeSearchDto){

        QType type = QType.type;

        Predicate used = type.used.eq(1);

        List<Type> typeList = from(type)
                .select(type)
                .where(used)
                .fetch();

        return typeList;
    }




}
