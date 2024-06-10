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
@Table(name="stock_record")
public class StockRecord extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long uid;


    @ManyToOne
    @JoinColumn(name="item_uid")
    private Item item;

    @ManyToOne
    @JoinColumn(name="company_uid")
    private Company company;

    @ManyToOne
    @JoinColumn(name="out_factory_uid")
    private Factory outFactory;

    @ManyToOne
    @JoinColumn(name="out_factory_sub_uid")
    private FactorySub outFactorySub;

    @ManyToOne
    @JoinColumn(name="in_factory_uid")
    private Factory inFactory;

    @ManyToOne
    @JoinColumn(name="in_factory_sub_uid")
    private FactorySub inFactorySub;



    @Column(nullable = false)
    private String lot; // 로트


    @Column(nullable = false)
    private Double qty; // 수량

    @Column(nullable = false)
    private String unit; // 단위
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String reason;



}
