package com.springboot.java_eco.data.repository.item;

import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.ItemController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemRepositoryCustomImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {

    public ItemRepositoryCustomImpl(){
        super(Item.class);
    }
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ItemController.class);

    @Override
    public List<Item> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QItem item = QItem.item;
        QCompany company = QCompany.company;
        QType type = QType.type;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (item.code != null) {
                builder.or(item.code.like("%" + search_text + "%"));
            }
            if (item.company != null) {
                builder.or(company.name.like("%" + search_text + "%"));
            }
            if (item.type != null) {
                builder.or(type.name.like("%" + search_text + "%"));
            }

        }else {
            if("code".equals(filter_title)){
                builder.and(item.code.like("%" + search_text + "%"));
            }else if("company".equals(filter_title)){
                builder.and(company.name.like("%" + search_text + "%"));
            }
            else if("type".equals(filter_title)){
                builder.and(type.name.like("%" + search_text + "%"));
            }

        }
        //Predicate dateRange = item.created.between(start_date, end_date);
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = item.used.eq(1);
        Predicate predicate = builder.getValue();


        List<Tuple> results = from(item)
                .leftJoin(item.company, company).fetchJoin()
                .leftJoin(item.type, type).fetchJoin()
                .select(item,company,type)
                .where(predicate,used)
                .orderBy(item.created.desc()) // Order by created field in descending order
                .fetch();
        List<Item> itemList = new ArrayList<>();

        for (Tuple result : results) {
            Item itemEntity = result.get(item);
            itemList.add(itemEntity);
            LOGGER.info("[Entity] data: {}", itemEntity);
        }

        return itemList;
    }
    @Override
    public List<Item> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QItem item = QItem.item;

        Predicate used = item.used.eq(1);

        List<Item> itemList = from(item)
                .select(item)
                .where(used)
                .fetch();

        return itemList;
    }  @Override
    public List<Item> findMaterial(CommonInfoSearchDto commonInfoSearchDto){

        QItem item = QItem.item;

        Predicate type = item.type_code.eq("원료");

        List<Item> itemList = from(item)
                .select(item)
                .where(type)
                .fetch();

        return itemList;
    }


}