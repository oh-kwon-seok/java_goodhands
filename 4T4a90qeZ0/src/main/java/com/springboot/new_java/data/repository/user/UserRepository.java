package com.springboot.new_java.data.repository.user;

import com.springboot.new_java.data.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepositorySupport")
public interface UserRepository extends JpaRepository<User,String>, UserRepositoryCustom {

    User getById(String id);


    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByAdminId(@Param("id") String id);

    Optional<User> findByKakaoId(Long kakaoId);  // 카카오 로그인용
    Optional<User> findByUid(Long uid);
    boolean existsByPhone(String phone);
}
