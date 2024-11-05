package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>,  ReceiptCustomRepository{
}
