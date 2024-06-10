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
@Table(name="work_task_packing")
public class WorkTaskPacking extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long uid;

    @ManyToOne
    @JoinColumn(name="work_task_uid")
    private WorkTask workTask;


    @ManyToOne
    @JoinColumn(name="work_task_product_uid")
    private WorkTaskProduct workTaskProduct;

    @ManyToOne
    @JoinColumn(name="bom_uid")
    private Bom bom;


    @ManyToOne
    @JoinColumn(name="factory_uid")
    private Factory factory;


    @ManyToOne
    @JoinColumn(name="factory_sub_uid")
    private FactorySub factorySub;


    @Column(nullable = false)
    private String lot; // lot

    @Column(nullable = false)
    private Integer inbox_qty; // 기준수량
    @Column(nullable = false)
    private Integer box_qty; // 박스 갯수
    @Column(nullable = false)
    private Integer etc_qty; // 실제 낱개 수량


    @Formula("(SELECT COALESCE(SUM(wtp.product_qty), 0) FROM work_task_product wtp WHERE wtp.work_task_uid = uid)")
    private BigDecimal totalEtcQty;



}
