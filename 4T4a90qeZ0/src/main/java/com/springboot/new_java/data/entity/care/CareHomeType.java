package com.springboot.new_java.data.entity.care;

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
@Table(name="care_home_type")
public class CareHomeType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column
    private String name;

    @Column(nullable = false)
    private Boolean used;

}
