package com.springboot.new_java.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="department")
public class Department extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;


    @Column(nullable = false)
    private String name;




    @Column(columnDefinition = "BIT")
    private Boolean used;


}
