package com.springboot.new_java.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass // JPA 엔티티가 자식 엔티티에게 매핑정보 전달
@EntityListeners(AuditingEntityListener.class) // 엔티티를 DB에 적용하기 전후로 콜백요청 가능하게처리
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    @LastModifiedDate
    private LocalDateTime deleted;




}
