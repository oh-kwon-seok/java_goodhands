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
@Table(name="restric_material_country")
public class RestricMaterialCountry extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false)
    private String regulate_type; // 구분


    @Column
    private String regl_code; // 규제코드

    @Column
    private String ingr_code; // 성분코드

    @Column
    private String country_name; // 국가명

    @Column
    private String notice_ingr_name; // 고시성분명

    @Column
    private String provis_atrcl; // 단서조항

    @Column
    private String limit_cond; // 제한사항




    @Column(nullable = false)
    private Integer used;


}
