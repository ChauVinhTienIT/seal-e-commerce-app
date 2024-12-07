package com.seal.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "list_price")
    private Double listPrice;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "enabled")
    private Integer enabled;

}