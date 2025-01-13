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
@Table(name="factory_sub")
public class FactorySub extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)  // ToOne은 fetch = FetchType.LAZY로 꼭 !!! 세팅
    @JoinColumn(name="factory_uid")
    private Factory factory;


    @Column(nullable = false)
    private String name;

    @Column
    private String status;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer used;


}
