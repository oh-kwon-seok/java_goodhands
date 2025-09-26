package com.springboot.new_java.data.entity.disease;


import com.springboot.new_java.data.entity.BaseEntity;
import com.springboot.new_java.data.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="disease_category")
public class DiseaseCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;



    @Column(nullable = false)
    private Boolean used;


}
