package com.springboot.new_java.data.repository.disease;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.disease.Disease;
import com.springboot.new_java.data.entity.disease.QDisease;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiseaseRepositoryCustomImpl extends QuerydslRepositorySupport implements DiseaseRepositoryCustom {

    public DiseaseRepositoryCustomImpl(){
        super(Disease.class);
    }

    @Override
    public List<Disease> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QDisease disease = QDisease.disease;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (disease.name != null) {
                builder.or(disease.name.like("%" + search_text + "%"));
            }

        }else {
            if("name".equals(filter_title)){
                builder.and(disease.name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = disease.used.eq(true);
        Predicate predicate = builder.getValue();
        List<Disease> diseaseList = from(disease)
                .select(disease)
                .where(predicate,used)
                .orderBy(disease.created.desc())
                .fetch();

        return diseaseList;
    }
    @Override
    public List<Disease> findInfo(){

        QDisease disease = QDisease.disease;

        Predicate used = disease.used.eq(true);

        List<Disease> diseaseList = from(disease)
                .select(disease)
                .where(used)
                .orderBy(disease.created.desc())
                .fetch();

        return diseaseList;
    }




}
