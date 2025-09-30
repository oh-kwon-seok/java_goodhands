package com.springboot.new_java.data.entity.care;

import com.springboot.new_java.data.entity.BaseEntity;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.data.entity.todo.TodoList;
import jakarta.persistence.*;
import lombok.*;




@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="care_schedule")
public class CareSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;




    @Column(name="care_reserve_date")
    private  String careReserveDate;

    @Column
    private String care_real_date;



    @ManyToOne
    @JoinColumn(name="caregiver_id")
    private User caregiver;

    @ManyToOne
    @JoinColumn(name="senior_uid")
    private Senior senior;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private Boolean used;

}
