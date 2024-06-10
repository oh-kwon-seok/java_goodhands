package com.springboot.java_eco.data.repository.department;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Department;
import com.springboot.java_eco.data.entity.QDepartment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DepartmentRepositoryCustomImpl extends QuerydslRepositorySupport implements DepartmentRepositoryCustom {

    public DepartmentRepositoryCustomImpl(){
        super(Department.class);
    }

    @Override
    public List<Department> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QDepartment department = QDepartment.department;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();


        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (department.name != null) {
                builder.or(department.name.like("%" + search_text + "%"));
            }
            if (department.company.name != null) {
                builder.or(department.company.name.like("%" + search_text + "%"));
            }
        }else {
            if("name".equals(filter_title)){
                builder.and(department.name.like("%" + search_text + "%"));
            }else if("company".equals(filter_title)){
                builder.and(department.company.name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = department.used.eq(1);
        Predicate predicate = builder.getValue();
        List<Department> departmentList = from(department)
                .select(department)
                .where(predicate,used)
                .fetch();

        return departmentList;
    }
    @Override
    public List<Department> findInfo(CommonInfoSearchDto DepartmentSearchDto){

        QDepartment department = QDepartment.department;

        Predicate used = department.used.eq(1);

        List<Department> departmentList = from(department)
                .select(department)
                .where(used)
                .fetch();

        return departmentList;
    }




}
