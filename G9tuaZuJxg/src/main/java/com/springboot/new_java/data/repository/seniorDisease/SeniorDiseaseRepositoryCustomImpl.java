package com.springboot.new_java.data.repository.seniorDisease;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.senior.QSeniorDisease;
import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.data.entity.senior.SeniorDisease;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeniorDiseaseRepositoryCustomImpl extends QuerydslRepositorySupport implements SeniorDiseaseRepositoryCustom {

    public SeniorDiseaseRepositoryCustomImpl(){
        super(Senior.class);
    }

    @Override
    public List<SeniorDisease> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QSeniorDisease seniorDisease = QSeniorDisease.seniorDisease;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (seniorDisease.disease.name != null) {
                builder.or(seniorDisease.disease.name.like("%" + search_text + "%"));
            }  if (seniorDisease.note != null) {
                builder.or(seniorDisease.note.like("%" + search_text + "%"));
            }


        }else {
            if("name".equals(filter_title)){
                builder.and(seniorDisease.disease.name.like("%" + search_text + "%"));
            }else if("note".equals(filter_title)){
                builder.and(seniorDisease.note.like("%" + search_text + "%"));
            }

        }

        Predicate predicate = builder.getValue();


        List<SeniorDisease> seniorDiseaseList = from(seniorDisease)
                .select(seniorDisease)
                .where(predicate)
                .fetch();

        return seniorDiseaseList;
    }





}
