package com.bwmanager.jaegwan.restaurant.repository;

import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
