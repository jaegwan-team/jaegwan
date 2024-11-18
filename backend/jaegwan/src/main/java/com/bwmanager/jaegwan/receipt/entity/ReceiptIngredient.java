package com.bwmanager.jaegwan.receipt.entity;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ReceiptIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private int price;

    private LocalDate expirationDate;

    @Column(nullable = false)
    private boolean isConfirmed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public void confirm(Ingredient ingredient, double amount, int price, LocalDate expirationDate) {
        isConfirmed = true;
        this.amount = amount;
        this.price = price;
        this.expirationDate = expirationDate;
        this.ingredient = ingredient;
    }
}
