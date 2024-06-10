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
@Table(name="bom")
public class Bom extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_uid")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_uid")
    private Item item;


    @Column(nullable = false)
    private Long parent_uid;

    @Column(nullable = false)
    private Long main;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Double qty;

    @Column
    private Double rate;


    @Column(nullable = false)
    private Integer used;


}
