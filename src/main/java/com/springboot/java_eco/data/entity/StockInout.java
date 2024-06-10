package com.springboot.java_eco.data.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="stock_inout")
public class StockInout extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;


    @Column(nullable = false)
    private String doc_type; // 문서 테이블명


    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name="company_uid")
    private Company company;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



}
