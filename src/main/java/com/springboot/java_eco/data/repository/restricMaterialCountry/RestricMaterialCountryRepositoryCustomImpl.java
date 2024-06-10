package com.springboot.java_eco.data.repository.restricMaterialCountryCountry;

import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.RestricMaterialCountryController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.QRestricMaterialCountry;
import com.springboot.java_eco.data.entity.RestricMaterialCountry;
import com.springboot.java_eco.data.repository.restricMaterialCountry.RestricMaterialCountryRepositoryCustom;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RestricMaterialCountryRepositoryCustomImpl extends QuerydslRepositorySupport implements RestricMaterialCountryRepositoryCustom {

    public RestricMaterialCountryRepositoryCustomImpl(){
        super(RestricMaterialCountry.class);
    }
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(RestricMaterialCountryController.class);

    @Override
    public List<RestricMaterialCountry> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QRestricMaterialCountry restricMaterialCountry = QRestricMaterialCountry.restricMaterialCountry;
        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (restricMaterialCountry.regulate_type != null) {
                builder.or(restricMaterialCountry.regulate_type.like("%" + search_text + "%"));
            }
            if (restricMaterialCountry.regl_code != null) {
                builder.or(restricMaterialCountry.regl_code.like("%" + search_text + "%"));
            }

            if (restricMaterialCountry.ingr_code != null) {
                builder.or(restricMaterialCountry.ingr_code.like("%" + search_text + "%"));
            }
      
            if (restricMaterialCountry.country_name != null) {
                builder.or(restricMaterialCountry.country_name.like("%" + search_text + "%"));
            }
            if (restricMaterialCountry.notice_ingr_name != null) {
                builder.or(restricMaterialCountry.notice_ingr_name.like("%" + search_text + "%"));
            }
            if (restricMaterialCountry.provis_atrcl != null) {
                builder.or(restricMaterialCountry.provis_atrcl.like("%" + search_text + "%"));
            }
            if (restricMaterialCountry.limit_cond != null) {
                builder.or(restricMaterialCountry.limit_cond.like("%" + search_text + "%"));
            }



        }else {
            if("regulate_type".equals(filter_title)){
                builder.and(restricMaterialCountry.regulate_type.like("%" + search_text + "%"));
            }else if("regl_code".equals(filter_title)){
                builder.and(restricMaterialCountry.regl_code.like("%" + search_text + "%"));
            }
            else if("ingr_code".equals(filter_title)){
                builder.and(restricMaterialCountry.ingr_code.like("%" + search_text + "%"));
            }
         
            else if("country_name".equals(filter_title)){
                builder.and(restricMaterialCountry.country_name.like("%" + search_text + "%"));
            }
            else if("notice_ingr_name".equals(filter_title)){
                builder.and(restricMaterialCountry.notice_ingr_name.like("%" + search_text + "%"));
            }
            else if("provis_atrcl".equals(filter_title)){
                builder.and(restricMaterialCountry.provis_atrcl.like("%" + search_text + "%"));
            }
            else if("limit_cond".equals(filter_title)){
                builder.and(restricMaterialCountry.limit_cond.like("%" + search_text + "%"));
            }


        }
         // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = restricMaterialCountry.used.eq(1);
        Predicate predicate = builder.getValue();


        List<RestricMaterialCountry>  restricMaterialCountryList = from(restricMaterialCountry)
                .select(restricMaterialCountry)
                .where(predicate,used)
                .orderBy(restricMaterialCountry.created.desc()) // Order by created field in descending order
                .fetch();
        return restricMaterialCountryList;
    }
    @Override
    public List<RestricMaterialCountry> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QRestricMaterialCountry restricMaterialCountry = QRestricMaterialCountry.restricMaterialCountry;

        Predicate used = restricMaterialCountry.used.eq(1);

        List<RestricMaterialCountry> restricMaterialCountryList = from(restricMaterialCountry)
                .select(restricMaterialCountry)
                .where(used)
                .fetch();

        return  restricMaterialCountryList;
    }



}