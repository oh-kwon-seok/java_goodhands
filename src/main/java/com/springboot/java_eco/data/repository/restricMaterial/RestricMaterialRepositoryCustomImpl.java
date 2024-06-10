package com.springboot.java_eco.data.repository.restricMaterial;

import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.RestricMaterialController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.RestricMaterial;
import com.springboot.java_eco.data.entity.QRestricMaterial;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RestricMaterialRepositoryCustomImpl extends QuerydslRepositorySupport implements RestricMaterialRepositoryCustom {

    public RestricMaterialRepositoryCustomImpl(){
        super(RestricMaterial.class);
    }
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(RestricMaterialController.class);

    @Override
    public List<RestricMaterial> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QRestricMaterial restricMaterial = QRestricMaterial.restricMaterial;
        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (restricMaterial.regulate_type != null) {
                builder.or(restricMaterial.regulate_type.like("%" + search_text + "%"));
            }
            if (restricMaterial.ingr_std_name != null) {
                builder.or(restricMaterial.ingr_std_name.like("%" + search_text + "%"));
            }

            if (restricMaterial.ingr_eng_name != null) {
                builder.or(restricMaterial.ingr_eng_name.like("%" + search_text + "%"));
            }
            if (restricMaterial.cas_no != null) {
                builder.or(restricMaterial.cas_no.like("%" + search_text + "%"));
            }
            if (restricMaterial.ingr_synonym != null) {
                builder.or(restricMaterial.ingr_synonym.like("%" + search_text + "%"));
            }
            if (restricMaterial.country_name != null) {
                builder.or(restricMaterial.country_name.like("%" + search_text + "%"));
            }
            if (restricMaterial.notice_ingr_name != null) {
                builder.or(restricMaterial.notice_ingr_name.like("%" + search_text + "%"));
            }
            if (restricMaterial.provis_atrcl != null) {
                builder.or(restricMaterial.provis_atrcl.like("%" + search_text + "%"));
            }
            if (restricMaterial.limit_cond != null) {
                builder.or(restricMaterial.limit_cond.like("%" + search_text + "%"));
            }



        }else {
            if("regulate_type".equals(filter_title)){
                builder.and(restricMaterial.regulate_type.like("%" + search_text + "%"));
            }else if("ingr_std_name".equals(filter_title)){
                builder.and(restricMaterial.ingr_std_name.like("%" + search_text + "%"));
            }
            else if("ingr_eng_name".equals(filter_title)){
                builder.and(restricMaterial.ingr_eng_name.like("%" + search_text + "%"));
            }
            else if("cas_no".equals(filter_title)){
                builder.and(restricMaterial.cas_no.like("%" + search_text + "%"));
            }
            else if("ingr_synonym".equals(filter_title)){
                builder.and(restricMaterial.ingr_synonym.like("%" + search_text + "%"));
            }
            else if("country_name".equals(filter_title)){
                builder.and(restricMaterial.country_name.like("%" + search_text + "%"));
            }
            else if("notice_ingr_name".equals(filter_title)){
                builder.and(restricMaterial.notice_ingr_name.like("%" + search_text + "%"));
            }
            else if("provis_atrcl".equals(filter_title)){
                builder.and(restricMaterial.provis_atrcl.like("%" + search_text + "%"));
            }
            else if("limit_cond".equals(filter_title)){
                builder.and(restricMaterial.limit_cond.like("%" + search_text + "%"));
            }


        }

        Predicate used = restricMaterial.used.eq(1);
        Predicate predicate = builder.getValue();


        List<RestricMaterial>  restricMaterialList = from(restricMaterial)
                .select(restricMaterial)
                .where(predicate,used)
                .orderBy(restricMaterial.created.desc()) // Order by created field in descending order
                .fetch();
        return restricMaterialList;
    }
    @Override
    public List<RestricMaterial> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QRestricMaterial restricMaterial = QRestricMaterial.restricMaterial;

        Predicate used = restricMaterial.used.eq(1);

        List<RestricMaterial> restricMaterialList = from(restricMaterial)
                .select(restricMaterial)
                .where(used)
                .fetch();

        return  restricMaterialList;
    }



}