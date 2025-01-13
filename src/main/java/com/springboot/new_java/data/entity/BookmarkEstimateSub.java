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
@Table(name="bookmark_estimate_sub")
public class BookmarkEstimateSub extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bookmark_estimate_uid")
    private BookmarkEstimate bookmarkEstimate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_uid")
    private Item item;

    @Column(nullable = false)
    private Double qty;

    @Column
    private String unit;

    @Column(nullable = false)
    private Integer buy_price;


    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer supply_price;

    @Column(nullable = false)
    private Integer vat_price;
    @Column
    private String description;




}
