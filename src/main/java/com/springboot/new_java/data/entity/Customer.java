package com.springboot.new_java.data.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="customer")
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long uid;
    @Column(nullable = false)
    private String code; // 사업자번호

    @Column(nullable = false)
    private String name; // 회사명

    @Column
    private String phone; // 연락처

    @Column
    private String email; // 이메일


    @Column(nullable = false)
    private Integer used;

}
