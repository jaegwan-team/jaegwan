package com.bwmanager.jaegwan.significant.entity;


import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Significant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private boolean isConfirmed;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurantId;

    @CreatedDate
    private Timestamp date;
}
