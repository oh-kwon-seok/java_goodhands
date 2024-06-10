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
@Table(name="ship_order_sub")
public class ShipOrderSub extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ship_order_uid")
    private ShipOrder shipOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_uid")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="stock_uid")
    private Stock stock;


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


}
