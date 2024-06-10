package com.springboot.java_eco.data.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="equipment_runtime")
public class EquipmentRuntime extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_uid")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="equipment_uid")
    private Equipment equipment;

    @Column(nullable = false)
    private LocalDateTime start_time; // 동작시간

    @Column(nullable = false)
    private LocalDateTime end_time; // 종료시간

    @Column(nullable = false)
    private Integer runtime_second; // 실제 동작한시간
    @Column(nullable = false)
    private Long used; // 사용유무


}
