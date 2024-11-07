package com.bwmanager.jaegwan.significant.repository;

import com.bwmanager.jaegwan.significant.entity.Significant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignificantRepository extends JpaRepository<Significant, Long> {
    List<Significant> findAllByRestaurantId(Long restaurantId);
}
