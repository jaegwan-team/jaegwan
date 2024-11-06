package com.bwmanager.jaegwan.receipt.entity;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private double amount;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private boolean isConfirmed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public void confirm(Ingredient ingredient) {
        isConfirmed = true;
        this.ingredient = ingredient;
    }
}
