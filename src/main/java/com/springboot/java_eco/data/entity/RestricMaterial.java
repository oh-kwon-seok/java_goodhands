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
@Table(name="restric_material")
public class RestricMaterial extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false)
    private String regulate_type; // 구분

    @Column
    private String ingr_std_name; // 표준명

    @Column
    private String ingr_eng_name; // 영문명

    @Column
    private String cas_no; //casNO

    @Column
    private String ingr_synonym; // 이명(별명)

    @Column
    private String country_name; // 배합금지국가

    @Column
    private String notice_ingr_name; // 고시원료명

    @Column
    private String provis_atrcl; // 단서조항

    @Column
    private String limit_cond; // 제한사항



    @Column(nullable = false)
    private Integer used;


}
