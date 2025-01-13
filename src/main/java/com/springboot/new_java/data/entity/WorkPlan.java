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
@Table(name="work_plan")
public class WorkPlan extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne
    @JoinColumn(name="company_uid")
    private Company company;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="bom_uid")
    private Bom bom;

    @Column(nullable = false)
    private String code; // 생산예정시작일

    @Column(name="start_date",nullable = false)
    private String startDate; // 생산예정시작일

    @Column(name="end_date",nullable = false)
    private String endDate; // 생산예정종료일

    @Column(nullable = false)
    private Double qty; // 계획수량

    @Column(nullable = false)
    private String unit; // 단위



    @Column(nullable = false)
    private String status; // 상태

    @Column(nullable = false)
    private Long used; // 사용유무

}
