package com.springboot.java_eco.data.repository.user;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.java_eco.controller.ItemController;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.User;
import com.springboot.java_eco.data.entity.QUser;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserRepositoryCustomImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryCustomImpl(){
        super(User.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ItemController.class);

    @Override
    public List<User> findAll(CommonInfoSearchDto commonInfoSearchDto){
        QUser user = QUser.user;


        String filter_title = commonInfoSearchDto.getFilter_title();
        String search_text = commonInfoSearchDto.getSearch_text();




        BooleanBuilder builder = new BooleanBuilder();


        if("all".equals(filter_title)){
            if (user.id != null) {
                builder.or(user.id.like("%" + search_text + "%"));
            }

            if (user.company.name != null) {
                builder.or(user.company.name.like("%" + search_text + "%"));
            }
            if (user.employment.name != null) {
                builder.or(user.employment.name.like("%" + search_text + "%"));
            }
            if (user.department.name != null) {
                builder.or(user.department.name.like("%" + search_text + "%"));
            }

            if (user.name != null) {
                builder.or(user.name.like("%" + search_text + "%"));
            }
            if (user.phone != null) {
                builder.or(user.phone.like("%" + search_text + "%"));
            }if (user.email != null) {
                builder.or(user.email.like("%" + search_text + "%"));
            }


        }else {
            if("id".equals(filter_title)){
                builder.and(user.id.like("%" + search_text + "%"));
            }
            else if("company".equals(filter_title)){
                builder.and(user.company.name.like("%" + search_text + "%"));
            }
            else if("employment".equals(filter_title)){
                builder.and(user.employment.name.like("%" + search_text + "%"));
            }
            else if("department".equals(filter_title)){
                builder.and(user.department.name.like("%" + search_text + "%"));
            }

            else if("name".equals(filter_title)){
                builder.and(user.name.like("%" + search_text + "%"));
            }
            else if("phone".equals(filter_title)){
                builder.and(user.phone.like("%" + search_text + "%"));
            }
            else if("email".equals(filter_title)){
                builder.and(user.email.like("%" + search_text + "%"));
            }


        }
        // used 필드가 1인 항목만 검색 조건 추가
        Predicate used = user.used.eq(1);

//        Predicate auth = user.auth.contains("ROLE_ADMIN");

        Predicate predicate = builder.getValue();


        List<User> userList = from(user)

                .select(user)
                .where(predicate,used)
                .orderBy(user.company.name.desc()) // Order by created field in descending order
                .fetch();

        return userList;

    }
    @Override
    public List<User> findInfo(CommonInfoSearchDto CommonInfoSearchDto){

        QUser user = QUser.user;

        Predicate used = user.used.eq(1);

        List<User> userList = from(user)
                .select(user)
                .where(used)
                .fetch();

        return userList;
    }




}
