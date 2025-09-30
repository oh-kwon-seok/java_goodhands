package com.springboot.new_java.data.repository.user;


import ch.qos.logback.classic.Logger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.springboot.new_java.controller.SignController;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.QUser;
import com.springboot.new_java.data.entity.User;

import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryCustomImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryCustomImpl(){
        super(User.class);
    }

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SignController.class);

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
        Predicate used = user.used.eq(true);

//        Predicate auth = user.auth.contains("ROLE_ADMIN");

        Predicate predicate = builder.getValue();


        List<User> userList = from(user)
                .select(user)
                .where(predicate,used)
                .orderBy(user.name.desc()) // Order by created field in descending order
                .fetch();


        return userList;

    }
    @Override
    public List<User> findInfo(CommonInfoSearchDto CommonInfoSearchDto){

        QUser user = QUser.user;

        Predicate used = user.used.eq(true);

        List<User> userList = from(user)
                .select(user)
                .where(used)
                .fetch();

        return userList;
    }




}
