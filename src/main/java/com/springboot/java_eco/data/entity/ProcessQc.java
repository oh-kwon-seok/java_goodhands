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
@Table(name="process_qc")
public class ProcessQc extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.LAZY)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="process_uid")
    private Process process;


    @Column(nullable = false)
    private String name;

    @Column
    private Integer type;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer used;


}
