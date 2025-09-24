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

@Table(name="senior_guardian")
public class SeniorGuardian  {

    @EmbeddedId
    private SeniorGuardianUid id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("seniorUid")
    @JoinColumn(name = "senior_uid")
    private Senior senior;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("guardianId")
    @JoinColumn(name = "guardian_id")
    private User guardian;

    @Column(name = "relation_type")
    private String relationType;


}

