package com.springboot.java_eco.data.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="estimate")
public class Estimate extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="company_uid")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="customer_uid")
    private Company customer;


    @ManyToOne(fetch = FetchType.EAGER)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="user_id")
    private User user;
    @Column(nullable = false)
    private String code; // 견적번호/발주번호 등
    @Column(nullable = false)
    private String name; // 제목

    @Column
    private String product_spec; // 제품사양

    @Column
    private String estimate_date; // 견적일자
    @Column
    private String expire; // 유효기간

    @Column
    private String ship_place; // 납품장소

    @Column
    private String description; // 발주조건 및 기타 특이사항

    @Column(nullable = false)
    private Integer used;

    @Formula("(SELECT COALESCE(SUM(es.buy_price), 0) FROM estimate_sub es WHERE es.estimate_uid = uid)")
    private BigDecimal totalBuyPrice;

    @Formula("(SELECT COALESCE(SUM(es.supply_price), 0) FROM estimate_sub es WHERE es.estimate_uid = uid)")
    private BigDecimal totalSupplyPrice;

    @Formula("(SELECT COALESCE(SUM(es.vat_price), 0) FROM estimate_sub es WHERE es.estimate_uid = uid)")
    private BigDecimal totalVatPrice;



}
