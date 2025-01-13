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
@Table(name="company")
public class Company extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long uid;
    @Column(nullable = false)
    private String code; // 사업자번호

    @Column(nullable = false)
    private String name; // 회사명

    @Column
    private String owner_name; // 대표자명

    @Column
    private String owner_phone; // 대표자 연락처

    @Column
    private String emp_name; // 담당자명

    @Column
    private String emp_phone; // 담당자 연락처

    @Column
    private String fax; // fax


    @Column
    private String email; // 연락처

    @Column
    private String type; // 매입/매출/사업장 구분

    @Column(nullable = false)
    private Integer used;

}
