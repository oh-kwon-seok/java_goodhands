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
@Table(name="bookmark_estimate")
public class BookmarkEstimate extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_uid")
    private Company company;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String name; // 제목

    @Column
    private String product_spec; // 제품사양

    @Column
    private String ship_place; // 납품장소

    @Column
    private String description; // 발주조건 및 기타 특이사항

    @Column(nullable = false)
    private Integer used;


}
