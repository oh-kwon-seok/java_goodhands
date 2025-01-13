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
@Table(name="equipment")
public class Equipment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_uid")
    private Company company;


    @Column(nullable = false)
    private String name; // 설비명

    @Column
    private String purpose; // 용도
    @Column
    private String description; // 비고

    @Column(nullable = false)
    private Integer used;

}
