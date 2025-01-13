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
@Table(name="stock_control")
public class StockControl extends BaseEntity{

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
    @JoinColumn(name="prev_factory_uid")
    private Factory prevFactory;

    @ManyToOne
    @JoinColumn(name="prev_factory_sub_uid")
    private FactorySub prevFactorySub;

    @ManyToOne
    @JoinColumn(name="after_factory_uid")
    private Factory afterFactory;

    @ManyToOne
    @JoinColumn(name="after_factory_sub_uid")
    private FactorySub afterFactorySub;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    @Column(nullable = false)
    private Double prev_qty; // 수량
    @Column(nullable = false)
    private Double after_qty; // 수량

    @Column(nullable = false)
    private String unit; // 단위

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String control_reason;





}
