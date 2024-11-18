package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.entity.ReceiptIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptIngredientRepository extends JpaRepository<ReceiptIngredient, Long>, ReceiptIngredientCustomRepository {

    void deleteAllByIsConfirmedFalseAndReceiptId(Long receiptId);
}
