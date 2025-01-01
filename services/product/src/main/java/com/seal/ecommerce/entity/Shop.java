package com.seal.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shops_id_gen")
    @SequenceGenerator(name = "shops_id_gen", sequenceName = "shop_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "shop")
    private Set<Product> products = new LinkedHashSet<>();

}