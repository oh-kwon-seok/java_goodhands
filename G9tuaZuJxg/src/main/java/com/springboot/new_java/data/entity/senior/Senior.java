package com.springboot.new_java.data.entity.senior;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springboot.new_java.data.entity.BaseEntity;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHome;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="senior")
public class Senior extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne
    @JoinColumn(name="user_id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "auth"})
    private User user;

    @Column(nullable = false)
    private String name;
    private String birth_date;

    @Column(nullable = false)
    private Boolean used;

    @ManyToOne
    @JoinColumn(name="caregiver_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "auth"})
    private User caregiver;

    @Column(nullable = false)
    private Boolean use_care_schedule;

    @Column
    private String care_schedule_protocol; // 케어 스케줄 생성규칙(주 1회 특정요일 + 기간 등 반복 유무)

    @ManyToOne
    @JoinColumn(name="care_home_uid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CareHome careHome;






}
