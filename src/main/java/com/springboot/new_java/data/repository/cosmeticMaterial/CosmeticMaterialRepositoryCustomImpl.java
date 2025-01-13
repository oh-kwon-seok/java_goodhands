package com.springboot.new_java.data.repository.cosmeticMaterial;

import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.CosmeticMaterialController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.QCosmeticMaterial;
import com.springboot.new_java.data.entity.CosmeticMaterial;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CosmeticMaterialRepositoryCustomImpl extends QuerydslRepositorySupport implements CosmeticMaterialRepositoryCustom {

    public CosmeticMaterialRepositoryCustomImpl(){
        super(CosmeticMaterial.class);
    }
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CosmeticMaterialController.class);

    @Override
    public List<CosmeticMaterial> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QCosmeticMaterial cosmeticMaterial = QCosmeticMaterial.cosmeticMaterial;
        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (cosmeticMaterial.ingr_kor_name != null) {
                builder.or(cosmeticMaterial.ingr_kor_name.like("%" + search_text + "%"));
            }

            if (cosmeticMaterial.ingr_eng_name != null) {
                builder.or(cosmeticMaterial.ingr_eng_name.like("%" + search_text + "%"));
            }
            if (cosmeticMaterial.cas_no != null) {
                builder.or(cosmeticMaterial.cas_no.like("%" + search_text + "%"));
            }
            if (cosmeticMaterial.ingr_synonym != null) {
                builder.or(cosmeticMaterial.ingr_synonym.like("%" + search_text + "%"));
            }
            if (cosmeticMaterial.origin_major_kor_name != null) {
                builder.or(cosmeticMaterial.origin_major_kor_name.like("%" + search_text + "%"));
            }

        }else {
            if("ingr_kor_name".equals(filter_title)){
                builder.and(cosmeticMaterial.ingr_kor_name.like("%" + search_text + "%"));
            }else if("ingr_eng_name".equals(filter_title)){
                builder.and(cosmeticMaterial.ingr_eng_name.like("%" + search_text + "%"));
            }
            else if("cas_no".equals(filter_title)){
                builder.and(cosmeticMaterial.cas_no.like("%" + search_text + "%"));
            }
            else if("ingr_synonym".equals(filter_title)){
                builder.and(cosmeticMaterial.ingr_synonym.like("%" + search_text + "%"));
            }
            else if("origin_major_kor_name".equals(filter_title)){
                builder.and(cosmeticMaterial.origin_major_kor_name.like("%" + search_text + "%"));
            }



        }
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = cosmeticMaterial.used.eq(1);
        Predicate predicate = builder.getValue();


        List<CosmeticMaterial>  cosmeticMaterialList = from(cosmeticMaterial)
                .select(cosmeticMaterial)
                .where(predicate,used)
                .orderBy(cosmeticMaterial.created.desc()) // Order by created field in descending order
                .fetch();
        return cosmeticMaterialList;
    }
    @Override
    public List<CosmeticMaterial> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QCosmeticMaterial cosmeticMaterial = QCosmeticMaterial.cosmeticMaterial;

        Predicate used = cosmeticMaterial.used.eq(1);

        List<CosmeticMaterial> cosmeticMaterialList = from(cosmeticMaterial)
                .select(cosmeticMaterial)
                .where(used)
                .fetch();

        return  cosmeticMaterialList;
    }



}