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
@Table(name="cosmetic_material")
public class CosmeticMaterial extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column
    private String ingr_kor_name; // 표준명

    @Column
    private String ingr_eng_name; // 영문명

    @Column
    private String cas_no; //casNO

    @Column
    private String ingr_synonym; // 이명(별명)

    @Column
    private String origin_major_kor_name; // 배합금지국가


    @Column(nullable = false)
    private Integer used;


}
