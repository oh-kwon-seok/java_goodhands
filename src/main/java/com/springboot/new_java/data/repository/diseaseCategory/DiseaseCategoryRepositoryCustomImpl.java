package com.springboot.new_java.data.repository.diseaseCategory;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.disease.DiseaseCategory;
import com.springboot.new_java.data.entity.disease.QDiseaseCategory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiseaseCategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements DiseaseCategoryRepositoryCustom {

    public DiseaseCategoryRepositoryCustomImpl(){
        super(DiseaseCategory.class);
    }

    @Override
    public List<DiseaseCategory> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QDiseaseCategory diseaseCategory = QDiseaseCategory.diseaseCategory;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (diseaseCategory.name != null) {
                builder.or(diseaseCategory.name.like("%" + search_text + "%"));
            }


        }else {
            if("name".equals(filter_title)){
                builder.and(diseaseCategory.name.like("%" + search_text + "%"));
            }

        }
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = diseaseCategory.used.eq(true);
        Predicate predicate = builder.getValue();


        List<DiseaseCategory> diseaseCategoryList = from(diseaseCategory)
                .select(diseaseCategory)
                .where(predicate,used)
                .fetch();

        return diseaseCategoryList;
    }
    @Override
    public List<DiseaseCategory> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QDiseaseCategory diseaseCategory = QDiseaseCategory.diseaseCategory;

        Predicate used = diseaseCategory.used.eq(true);

        List<DiseaseCategory> diseaseCategoryList = from(diseaseCategory)
                .select(diseaseCategory)
                .where(used)
                .fetch();

        return diseaseCategoryList;
    }




}
