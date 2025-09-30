package com.springboot.new_java.data.repository.careSchedule;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.care.CareSchedule;
import com.springboot.new_java.data.entity.care.QCareSchedule;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CareScheduleRepositoryCustomImpl extends QuerydslRepositorySupport implements CareScheduleRepositoryCustom {

    public CareScheduleRepositoryCustomImpl(){
        super(CareSchedule.class);
    }

    @Override
    public List<CareSchedule> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QCareSchedule CareSchedule = QCareSchedule.careSchedule;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();
        BooleanBuilder builder = new BooleanBuilder();

        if("all".equals(filter_title)){

            if (CareSchedule.careReserveDate != null) {
                builder.or(CareSchedule.careReserveDate.like("%" + search_text + "%"));
            }
            if (CareSchedule.care_real_date != null) {
                builder.or(CareSchedule.care_real_date.like("%" + search_text + "%"));
            } if (CareSchedule.senior.name != null) {
                builder.or(CareSchedule.senior.name.like("%" + search_text + "%"));
            } if (CareSchedule.caregiver.name != null) {
                builder.or(CareSchedule.caregiver.name.like("%" + search_text + "%"));
            }

        }else {
            if("care_reserve_date".equals(filter_title)){
                builder.and(CareSchedule.careReserveDate.like("%" + search_text + "%"));
            }
            else if("care_real_date".equals(filter_title)){
                builder.and(CareSchedule.care_real_date.like("%" + search_text + "%"));
            }
            else if("senior_name".equals(filter_title)){
                builder.and(CareSchedule.senior.name.like("%" + search_text + "%"));
            }
            else if("caregiver_name".equals(filter_title)){
                builder.and(CareSchedule.caregiver.name.like("%" + search_text + "%"));
            }

        }
        Predicate predicate = builder.getValue();
        Predicate used = CareSchedule.used.eq(true);

        List<CareSchedule> CareScheduleList = from(CareSchedule)
                .select(CareSchedule)
                .where(predicate,used)
                .fetch();

        return CareScheduleList;
    }
    @Override
    public List<CareSchedule> findInfo(){

        QCareSchedule CareSchedule = QCareSchedule.careSchedule;



        List<CareSchedule> CareScheduleList = from(CareSchedule)
                .select(CareSchedule)
                .where()
                .fetch();

        return CareScheduleList;
    }




}
