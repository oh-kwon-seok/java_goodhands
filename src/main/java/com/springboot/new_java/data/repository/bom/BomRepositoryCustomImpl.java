package com.springboot.new_java.data.repository.bom;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BomRepositoryCustomImpl extends QuerydslRepositorySupport implements BomRepositoryCustom {

    public BomRepositoryCustomImpl(){
        super(Bom.class);
    }
//    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ItemController.class);
    @Override
    public List<Bom> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QBom bom = QBom.bom;
        QCompany company = QCompany.company;
        QItem item = QItem.item;


        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();

        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (bom.code != null) {
                builder.or(bom.code.like("%" + search_text + "%"));
            }
            if (bom.item.ingr_kor_name != null) {
                builder.or(item.ingr_kor_name.like("%" + search_text + "%"));
            }
            if (bom.item.ingr_eng_name != null) {
                builder.or(item.ingr_eng_name.like("%" + search_text + "%"));
            }


        }else {
            if("code".equals(filter_title)){
                builder.and(bom.code.like("%" + search_text + "%"));
            }else if("ingr_kor_name".equals(filter_title)){
                builder.and(item.ingr_kor_name.like("%" + search_text + "%"));
            }else if("ingr_eng_name".equals(filter_title)){
                builder.and(item.ingr_eng_name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가


        Predicate used = bom.used.eq(1);
        Predicate predicate = builder.getValue();

        List<Tuple> results = from(bom)
                .leftJoin(bom.company, company).fetchJoin()
                .leftJoin(bom.item, item).fetchJoin()
                .select(bom,company,item)
                .where(predicate,used)
                .fetch();

        List<Bom> bomList = new ArrayList<>();

        for (Tuple result : results) {
            Bom bomEntity = result.get(bom);
            bomList.add(bomEntity);
//            LOGGER.info("[Entity] data: {}", bomEntity);
        }
        return bomList;
    }
    @Override
    public List<Bom> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QBom bom = QBom.bom;

        Predicate used = bom.used.eq(1);

        List<Bom> bomList = from(bom)
                .select(bom)
                .where(used)
                .fetch();

        return bomList;
    }




}
