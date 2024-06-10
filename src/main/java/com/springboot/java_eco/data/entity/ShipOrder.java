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
@Table(name="ship_order")
public class ShipOrder extends BaseEntity{

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
    @JoinColumn(name="order_uid")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="user_id")
    private User user;


    @Column(nullable = false)
    private String code; //출하번호
    @Column(nullable = false)
    private String name; // 출하명

    @Column
    private String product_spec; // 제품사양

    @Column
    private String ship_date; // 실제출하일자

    @Column
    private String ship_place; // 납품장소

    @Column
    private Integer order_count; // 주문일 대비 납기 날짜 차이

    @Column
    private String description; // 발주조건 및 기타 특이사항

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer used;

    @Formula("(SELECT COALESCE(SUM(so.buy_price), 0) FROM ship_order_sub so WHERE so.ship_order_uid = uid)")
    private BigDecimal totalBuyPrice;

    @Formula("(SELECT COALESCE(SUM(so.supply_price), 0) FROM ship_order_sub so WHERE so.ship_order_uid = uid)")
private BigDecimal totalSupplyPrice;

    @Formula("(SELECT COALESCE(SUM(so.vat_price), 0) FROM ship_order_sub so WHERE so.ship_order_uid = uid)")
    private BigDecimal totalVatPrice;



}
