package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>, ReceiptCustomRepository {
    Optional<Receipt> findByImageUrl(String imageUrl);
}
