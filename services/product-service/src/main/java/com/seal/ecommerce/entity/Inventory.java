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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_gen")
    @SequenceGenerator(name = "inventory_gen", sequenceName = "inventory_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "list_price")
    private Double listPrice;

    @Column(name = "discount_percent") // confuse
    private Double discountPercent;

    @Column(name = "cost") // confuse
    private Double cost;

    @Column(name = "enabled") // confuse
    private Integer enabled;

}