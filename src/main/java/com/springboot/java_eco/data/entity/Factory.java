package com.springboot.java_eco.data.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="factory")
public class Factory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="company_uid")
    private Company company;

    @Column(nullable = false)
    private String name;

    @Column
    private String status;

    @Column
    private String description;

    @Column
    private String factory_sub_array;

    @Column(nullable = false)
    private Integer used;


}
