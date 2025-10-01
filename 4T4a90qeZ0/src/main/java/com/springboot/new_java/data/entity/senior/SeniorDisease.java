package com.springboot.new_java.data.entity.senior;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
//@ToString(callSuper = true)

@Table(name="senior_disease")
public class SeniorDisease    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "senior_uid")
    @JsonIgnore
    private Senior senior;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disease_uid")
    private Disease disease;

    @Column
    private String diagnosis_date;

    @Column
    private String note;

    @Column
    private Boolean use_auto_checklist;



}

