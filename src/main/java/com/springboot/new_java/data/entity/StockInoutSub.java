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
@Table(name="stock_inout_sub")
public class StockInoutSub extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="stock_inout_uid")
    private StockInout stockInout;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="factory_uid")
    private Factory factory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="factory_sub_uid")
    private FactorySub factorySub;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_uid")
    private Item item;


    @Column(nullable = false)
    private String lot;


    @Column(nullable = false)
    private Double qty;

    @Column(nullable = false)
    private String unit;

    @Column
    private String status;






}
