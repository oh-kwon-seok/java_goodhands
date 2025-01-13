package com.springboot.new_java.data.repository.factorySub;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.FactorySubController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.factorySub.FactorySubSearchDto;
import com.springboot.new_java.data.entity.FactorySub;

import com.springboot.new_java.data.entity.QFactory;
import com.springboot.new_java.data.entity.QFactorySub;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FactorySubRepositoryCustomImpl extends QuerydslRepositorySupport implements FactorySubRepositoryCustom {

    public FactorySubRepositoryCustomImpl(){
        super(FactorySub.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(FactorySubController.class);


    @Override
    public List<FactorySub> findAll(FactorySubSearchDto factorySubSearchDto){

      
        QFactory factory = QFactory.factory;


        QFactorySub factorySub = QFactorySub.factorySub;



        String filter_title = factorySubSearchDto.getFilter_title();
        String search_text = factorySubSearchDto.getSearch_text();

        LocalDateTime start_date = factorySubSearchDto.getStart_date();
        LocalDateTime end_date = factorySubSearchDto.getEnd_date();
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
            if (factorySub.name != null) {
                builder.or(factorySub.name.like("%" + search_text + "%"));
            }
            if (factorySub.status != null) {
                builder.or(factorySub.status.like("%" + search_text + "%"));
            }
            if (factorySub.description != null) {
                builder.or(factorySub.description.like("%" + search_text + "%"));
            }


        }else {
            if("factory_name".equals(filter_title)){
                builder.and(factory.name.like("%" + search_text + "%"));
            }
            else if("factory_status".equals(filter_title)){
                builder.and(factory.status.like("%" + search_text + "%"));
            }
            else if("factory_description".equals(filter_title)){
                builder.and(factory.description.like("%" + search_text + "%"));
            }

            else if("name".equals(filter_title)){
                builder.and(factorySub.name.like("%" + search_text + "%"));
            } else if("status".equals(filter_title)){
                builder.and(factorySub.status.like("%" + search_text + "%"));
            } else if("description".equals(filter_title)){
                builder.and(factorySub.description.like("%" + search_text + "%"));
            }

        }

        Predicate dateRange = factorySub.created.between(start_date, end_date);

        Predicate predicate = builder.getValue();

        LOGGER.info("pre : {}",predicate);

        List<Tuple> results = from(factorySub)

                .leftJoin(factorySub.factory, factory).fetchJoin()
                .select(factorySub,factory)
                .where(predicate,dateRange)
                .orderBy(factorySub.created.desc())
                .fetch();

        List<FactorySub> factorySubList = new ArrayList<>();

        LOGGER.info("test5 : {}",factorySubList);
        for (Tuple result : results) {
            FactorySub factorySubEntity = result.get(factorySub);
            factorySubList.add(factorySubEntity);
        }
        return factorySubList;

    }



    @Override
    public List<FactorySub> findInfo(FactorySubSearchDto factorySubSearchDto){

        QFactory factory = QFactory.factory;
        QFactorySub factorySub = QFactorySub.factorySub;
        Long search_id = factorySubSearchDto.getFactory_uid();

        Predicate factory_uid = factory.uid.eq(search_id);

        List<Tuple> results = from(factorySub)
                .leftJoin(factorySub.factory, factory).fetchJoin()
                .select(factorySub,factory)
                .where(factory_uid)
                .fetch();
        List<FactorySub> factorySubList = new ArrayList<>();
        for (Tuple result : results) {
            FactorySub factorySubEntity = result.get(factorySub);
            factorySubList.add(factorySubEntity);
        }
        return factorySubList;

    }
    @Override
    public List<FactorySub> findInfoFactorySub(CommonInfoSearchDto commonSubSearchDto){

        QFactorySub factorySub = QFactorySub.factorySub;

        Predicate used = factorySub.used.eq(1);

        List<FactorySub> factorySubList = from(factorySub)
                .select(factorySub)
                .where(used)
                .fetch();

        return factorySubList;

    }



}
