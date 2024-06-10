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
@Table(name="order_sub")
public class OrderSub extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="order_uid")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_uid")
    private Item item;

    @Column(nullable = false)
    private Double qty;

    @Column
    private String unit;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer buy_price;


    @Column(nullable = false)
    private Integer supply_price;

    @Column(nullable = false)
    private Integer vat_price;
    @Column
    private String description;



}
