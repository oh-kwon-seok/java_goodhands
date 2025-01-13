package com.springboot.new_java.data.entity;


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
@Table(name="order")
public class Order extends BaseEntity{

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
    @JoinColumn(name="estimate_uid")
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.EAGER)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="user_id")
    private User user;


    @Column(nullable = false)
    private String code; // 발주번호
    @Column(nullable = false)
    private String name; // 발주명

    @Column
    private String product_spec; // 제품사양

    @Column
    private String ship_date; // 납기일자

    @Column
    private String ship_place; // 납품장소

    @Column
    private String description; // 발주조건 및 기타 특이사항

    @Column(nullable = false)
    private Integer used;

    @Formula("(SELECT COALESCE(SUM(os.buy_price), 0) FROM order_sub os WHERE os.order_uid = uid)")
    private BigDecimal totalBuyPrice;

    @Formula("(SELECT COALESCE(SUM(os.supply_price), 0) FROM order_sub os WHERE os.order_uid = uid)")
    private BigDecimal totalSupplyPrice;

    @Formula("(SELECT COALESCE(SUM(os.vat_price), 0) FROM order_sub os WHERE os.order_uid = uid)")
    private BigDecimal totalVatPrice;



}
