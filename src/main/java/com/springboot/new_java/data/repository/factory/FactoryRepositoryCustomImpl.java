package com.springboot.new_java.data.repository.factory;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import com.springboot.new_java.controller.FactoryController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FactoryRepositoryCustomImpl extends QuerydslRepositorySupport implements FactoryRepositoryCustom {

    public FactoryRepositoryCustomImpl(){
        super(Factory.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(FactoryController.class);

    
    @Override
    public List<Factory> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QFactory factory = QFactory.factory;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (factory.name != null) {
                builder.or(factory.name.like("%" + search_text + "%"));
            }
            if (factory.status != null) {
                builder.or(factory.status.like("%" + search_text + "%"));
            }
            if (factory.description != null) {
                builder.or(factory.description.like("%" + search_text + "%"));
            }
            if (factory.company.name != null) {
                builder.or(factory.company.name.like("%" + search_text + "%"));
            }


        }else {

            if("name".equals(filter_title)){
                builder.and(factory.name.like("%" + search_text + "%"));
            }
            else if("status".equals(filter_title)){
                builder.and(factory.status.like("%" + search_text + "%"));
            }
            else if("description".equals(filter_title)){
                builder.and(factory.description.like("%" + search_text + "%"));
            }  else if("company".equals(filter_title)){
                builder.and(factory.company.name.like("%" + search_text + "%"));
            }

        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = factory.used.eq(1);



        Predicate predicate = builder.getValue();

        List<Factory> factoryList = from(factory)
                .select(factory)
                .where(predicate,used)
                .orderBy(factory.created.desc()) // Order by created field in descending order
                .fetch();



        return factoryList;

    }

    @Override
    public List<Factory> findInfo(CommonInfoSearchDto commonSubSearchDto) {

        QFactory factory = QFactory.factory;

        Predicate used = factory.used.eq(1);

        List<Factory> factoryList = from(factory)
                .select(factory)
                .where(used)
                .fetch();

        return factoryList;

    }
}
