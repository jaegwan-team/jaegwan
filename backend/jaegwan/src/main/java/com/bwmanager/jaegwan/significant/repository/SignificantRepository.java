package com.bwmanager.jaegwan.significant.repository;

import com.bwmanager.jaegwan.significant.entity.Significant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignificantRepository extends JpaRepository<Significant, Long> {

    List<Significant> findAllByRestaurantIdOrderByIsConfirmed(Long restaurantId);

    List<Significant> findAllByRestaurantIdAndIsConfirmed(Long restaurantId, boolean confirmed);
}
