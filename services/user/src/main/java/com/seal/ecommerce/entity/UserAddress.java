package com.seal.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_addresses")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_addresses_id_gen")
    @SequenceGenerator(name = "user_addresses_id_gen", sequenceName = "user_address_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "address_line", length = Integer.MAX_VALUE)
    private String addressLine;

    @Column(name = "city", length = Integer.MAX_VALUE)
    private String city;

    @Column(name = "state", length = Integer.MAX_VALUE)
    private String state;

    @Column(name = "country", length = Integer.MAX_VALUE)
    private String country;

    @Column(name = "postal_code", length = Integer.MAX_VALUE)
    private String postalCode;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}