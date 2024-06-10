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
@Table(name="stock")
public class Stock extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long uid;

    @Column(nullable = false)
    private String lot; // 로트

    @ManyToOne
    @JoinColumn(name="item_uid")
    private Item item;

    @ManyToOne
    @JoinColumn(name="company_uid")
    private Company company;

    @ManyToOne
    @JoinColumn(name="factory_uid")
    private Factory factory;

    @ManyToOne
    @JoinColumn(name="factory_sub_uid")
    private FactorySub factorySub;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    @Column(nullable = false)
    private Double qty; // 수량

    @Column
    private String unit; // 단위

    @Column(nullable = false)
    private String status;





}
