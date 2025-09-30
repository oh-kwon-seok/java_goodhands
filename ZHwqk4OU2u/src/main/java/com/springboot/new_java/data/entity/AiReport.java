package com.springboot.new_java.data.entity;


import com.springboot.new_java.data.entity.care.CareLog;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="ai_report")
public class AiReport extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "care_log_uid")
    private CareLog careLog;

    @Column
    private String ai_report;


}
