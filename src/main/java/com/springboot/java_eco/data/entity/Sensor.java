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
@Table(name="sensor")
public class Sensor extends BaseEntity{
    // 센서장치만 해당됌, 설비랑은 관계없음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false)
    private String code; // 장비 코드 (디바이스명)  - ECO-000-001

    @Column(nullable = false)
    private Double data; // 데이터

    @Column(nullable = false)
    private String type; // 온도/습도/전자저울/PH측정  -> TEMP    / HUMI   /  WEIGHT    // PH

    @Column(nullable = false)
    private String comment; // 코멘트

}
