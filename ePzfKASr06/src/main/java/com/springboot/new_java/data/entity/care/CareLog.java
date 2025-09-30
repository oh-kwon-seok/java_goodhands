package com.springboot.new_java.data.entity.care;

import com.springboot.new_java.data.entity.BaseEntity;
import com.springboot.new_java.data.entity.User;
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
@Table(name="care_log")
public class CareLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="care_schedule_uid")
    private CareSchedule careSchedule;

    @Column
    private String caregiver_checklist;

    @Column
    private String state_time;

    @Column
    private String end_time;


    @Column
    private String file_path;

}
