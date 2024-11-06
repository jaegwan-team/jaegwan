package com.bwmanager.jaegwan.significant.repository;

import com.bwmanager.jaegwan.significant.entity.SignificantIngredient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignificantIngredientRepository extends JpaRepository<SignificantIngredient, Long> {
    List<SignificantIngredient> findAllBySignificantId(Long id);

    void deleteBySignificant_Id(Long significantId);
}
