package com.springboot.new_java.data.repository.company;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.entity.QCompany;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyRepositoryCustomImpl extends QuerydslRepositorySupport implements CompanyRepositoryCustom {

    public CompanyRepositoryCustomImpl(){
        super(Company.class);
    }

    @Override
    public List<Company> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QCompany company = QCompany.company;

        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();



        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){

            if (company.code != null) {
                builder.or(company.code.like("%" + search_text + "%"));
            }
            if (company.name != null) {
                builder.or(company.name.like("%" + search_text + "%"));
            }
            if (company.email != null) {
                builder.or(company.email.like("%" + search_text + "%"));
            }
            if (company.owner_name != null) {
                builder.or(company.owner_name.like("%" + search_text + "%"));
            }
            if (company.emp_name != null) {
                builder.or(company.emp_name.like("%" + search_text + "%"));
            }

        }else {
            if("code".equals(filter_title)){
                builder.and(company.code.like("%" + search_text + "%"));
            }
            else if("name".equals(filter_title)){
                builder.and(company.name.like("%" + search_text + "%"));
            }else if("email".equals(filter_title)){
                builder.and(company.email.like("%" + search_text + "%"));
            }else if("owner_name".equals(filter_title)){
                builder.and(company.owner_name.like("%" + search_text + "%"));
            }else if("emp_name".equals(filter_title)){
                builder.and(company.emp_name.like("%" + search_text + "%"));
            }
        }

        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = company.used.eq(1);
        Predicate predicate = builder.getValue();
        List<Company> companyList = from(company)
                .select(company)
                .where(predicate,used)
                .fetch();

        return companyList;
    }
    @Override
    public List<Company> findInfo(CommonInfoSearchDto commonInfoSearchDto){

        QCompany company = QCompany.company;

        Predicate used = company.used.eq(1);

        List<Company> companyList = from(company)
                .select(company)
                .where(used)
                .fetch();

        return companyList;
    }
    @Override
    public List<Company> findInfoCustomer(CommonInfoSearchDto commonInfoSearchDto){

        QCompany company = QCompany.company;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(company.type.like("%매출%"));
        Predicate used = company.used.eq(1);


        Predicate predicate = builder.getValue();


        List<Company> companyList = from(company)
                .select(company)
                .where(predicate,used)
                .fetch();

        return companyList;
    }




}
