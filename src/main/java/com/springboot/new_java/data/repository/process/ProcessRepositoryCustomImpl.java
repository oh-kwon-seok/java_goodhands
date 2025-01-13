package com.springboot.new_java.data.repository.process;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.ProcessController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Process;
import com.springboot.new_java.data.entity.QProcess;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessRepositoryCustomImpl extends QuerydslRepositorySupport implements ProcessRepositoryCustom {

    public ProcessRepositoryCustomImpl(){
        super(Process.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProcessController.class);

    
    @Override
    public List<Process> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QProcess process = QProcess.process;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (process.name != null) {
                builder.or(process.name.like("%" + search_text + "%"));
            }
            if (process.status != null) {
                builder.or(process.status.like("%" + search_text + "%"));
            }
            if (process.description != null) {
                builder.or(process.description.like("%" + search_text + "%"));
            }
            if (process.company.name != null) {
                builder.or(process.company.name.like("%" + search_text + "%"));
            }


        }else {

            if("name".equals(filter_title)){
                builder.and(process.name.like("%" + search_text + "%"));
            }
            else if("status".equals(filter_title)){
                builder.and(process.status.like("%" + search_text + "%"));
            }
            else if("description".equals(filter_title)){
                builder.and(process.description.like("%" + search_text + "%"));
            }  else if("company".equals(filter_title)){
                builder.and(process.company.name.like("%" + search_text + "%"));
            }

        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = process.used.eq(1);



        Predicate predicate = builder.getValue();

        List<Process> processList = from(process)
                .select(process)
                .where(predicate,used)
                .orderBy(process.created.desc()) // Order by created field in descending order
                .fetch();



        return processList;

    }

    @Override
    public List<Process> findInfo(CommonInfoSearchDto commonSubSearchDto){

        QProcess process = QProcess.process;

        Predicate used = process.used.eq(1);

        List<Process> processList = from(process)
                .select(process)
                .where(used)
                .fetch();

        return processList;

    }
}
