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
@Table(name="work_task")
public class WorkTask extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @ManyToOne
    @JoinColumn(name="work_plan_uid")
    private WorkPlan workPlan;
    @ManyToOne
    @JoinColumn(name="company_uid")
    private Company company;

    @ManyToOne
    @JoinColumn(name="bom_uid")
    private Bom bom;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private Double task_qty; // 지시수량

    @Column(nullable = false)
    private Double success_qty; // 합격수량

    @Column(nullable = false)
    private Double fail_qty; // 합격수량

    @Column(nullable = false)
    private String work_start_date; // 생산지시일

    @Column(nullable = false)
    private String work_end_date; // 생산종료일



    @Column(nullable = false)
    private String unit; // 단위

    @Column(nullable = false)
    private String status; // 상태

    @Column(nullable = false)
    private Long material_order; //자재불출요청

    @Column(nullable = false)
    private Long measure_order; // 계량지시

    @Column(nullable = false)
    private Long production_order; // 제조지시

    @Column(nullable = false)
    private Long packing_order; //포장지시



}
