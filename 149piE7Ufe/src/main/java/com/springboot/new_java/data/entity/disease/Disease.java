package com.springboot.new_java.data.entity.disease;


import com.springboot.new_java.data.entity.BaseEntity;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.senior.SeniorDisease;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="disease")
public class Disease extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;


    @Column
    private String description;

    @Column(nullable = false)
    private Boolean used;

    @Column(nullable = false)
    private Boolean use_chronic; // 만성유무

    @Column(nullable = false)
    private Boolean use_disease_checklist; // 질병유형별 체크리스트를 자동적용할지 말지 유무(기본 true)

    @Column
    private String disease_checklist; // 질병유형별 체크리스트(JSON)

    @ManyToOne
    @JoinColumn(name="disease_category_uid")
    private DiseaseCategory diseaseCategory;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeniorDisease> seniorDiseases = new ArrayList<>();



}
