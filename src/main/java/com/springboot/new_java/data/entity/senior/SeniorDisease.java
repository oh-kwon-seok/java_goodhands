package com.springboot.new_java.data.entity.senior;


import com.springboot.new_java.data.entity.BaseEntity;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.disease.Disease;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="senior_disease")
public class SeniorDisease extends BaseEntity {

    @EmbeddedId
    private SeniorDiseaseUid id;

    @ManyToOne
    @MapsId("seniorUid")
    @JoinColumn(name = "senior_uid")
    private Senior senior;

    @ManyToOne
    @MapsId("diseaseUid")
    @JoinColumn(name = "disease_uid")
    private Disease disease;

    @Column(name = "diagnosis_date")
    private LocalDate diagnosisDate;

    @Column
    private String note;

    @Column
    private Boolean is_auto_checklist;




}

