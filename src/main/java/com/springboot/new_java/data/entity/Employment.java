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
@Table(name="employment")
public class Employment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;


    @Column(nullable = false)
    private String name;

    private String name2;




    @Column(nullable = false)
    private Boolean used;


}
