package com.springboot.new_java.data.repository.processQc;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.ProcessQcController;
import com.springboot.new_java.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.new_java.data.entity.ProcessQc;
import com.springboot.new_java.data.entity.QProcess;
import com.springboot.new_java.data.entity.QProcessQc;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessQcRepositoryCustomImpl extends QuerydslRepositorySupport implements ProcessQcRepositoryCustom {

    public ProcessQcRepositoryCustomImpl(){
        super(ProcessQc.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProcessQcController.class);


    @Override
    public List<ProcessQc> findAll(ProcessQcSearchDto processQcSearchDto){

      
        QProcess process = QProcess.process;


        QProcessQc processQc = QProcessQc.processQc;



        String filter_title = processQcSearchDto.getFilter_title();
        String search_text = processQcSearchDto.getSearch_text();


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
            if (processQc.name != null) {
                builder.or(processQc.name.like("%" + search_text + "%"));
            }
            if (processQc.type != null) {
                builder.or(processQc.type.like("%" + search_text + "%"));
            }
            if (processQc.description != null) {
                builder.or(processQc.description.like("%" + search_text + "%"));
            }


        }else {
            if("process_name".equals(filter_title)){
                builder.and(process.name.like("%" + search_text + "%"));
            }
            else if("process_status".equals(filter_title)){
                builder.and(process.status.like("%" + search_text + "%"));
            }
            else if("process_description".equals(filter_title)){
                builder.and(process.description.like("%" + search_text + "%"));
            }

            else if("name".equals(filter_title)){
                builder.and(processQc.name.like("%" + search_text + "%"));
            } else if("type".equals(filter_title)){
                builder.and(processQc.type.like("%" + search_text + "%"));
            } else if("description".equals(filter_title)){
                builder.and(processQc.description.like("%" + search_text + "%"));
            }

        }

        Predicate predicate = builder.getValue();

        LOGGER.info("pre : {}",predicate);

        List<Tuple> results = from(processQc)

                .leftJoin(processQc.process, process).fetchJoin()
                .select(processQc,process)
                .where(predicate)
                .orderBy(processQc.created.desc())
                .fetch();

        List<ProcessQc> processQcList = new ArrayList<>();

        LOGGER.info("test5 : {}",processQcList);
        for (Tuple result : results) {
            ProcessQc processQcEntity = result.get(processQc);
            processQcList.add(processQcEntity);
        }
        return processQcList;

    }



    @Override
    public List<ProcessQc> findInfo(ProcessQcSearchDto processQcSearchDto){

        QProcess process  = QProcess.process;
        QProcessQc processQc = QProcessQc.processQc;
        Long search_id = processQcSearchDto.getProcess_uid();

        Predicate process_uid = process.uid.eq(search_id);

        List<Tuple> results = from(processQc)
                .leftJoin(processQc.process, process).fetchJoin()
                .select(processQc,process)
                .where(process_uid)
                .fetch();
        List<ProcessQc> processQcList = new ArrayList<>();
        for (Tuple result : results) {
            ProcessQc processQcEntity = result.get(processQc);
            processQcList.add(processQcEntity);
        }
        return processQcList;

    }



}
