package com.springboot.new_java.data.entity.todo;


import com.springboot.new_java.data.entity.BaseEntity;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareSchedule;
import com.springboot.new_java.data.entity.senior.Senior;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="todo_list")
public class TodoList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="care_schedule_uid")
    private CareSchedule caregiverSchedule;

    @Column
    private String todo_list;



}
