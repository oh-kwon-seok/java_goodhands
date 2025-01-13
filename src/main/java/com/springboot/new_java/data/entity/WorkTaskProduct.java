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
@Table(name="work_task_product")
public class WorkTaskProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long uid;

    @ManyToOne
    @JoinColumn(name="work_task_uid")
    private WorkTask workTask;

    @ManyToOne
    @JoinColumn(name="bom_uid")
    private Bom bom;



    @Column(nullable = false)
    private String lot; // lot

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double product_qty; // 생산 수량

    @Column
    private String unit;


}
