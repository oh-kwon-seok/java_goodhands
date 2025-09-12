package com.springboot.new_java.data.repository.user;

import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.Employment;
import com.springboot.new_java.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositorySupport")
public interface UserRepository extends JpaRepository<User,String>, UserRepositoryCustom {

    User getById(String id);

    User findByIdAndEmploymentAndDepartment(String id,Employment employment, Department department);



}
