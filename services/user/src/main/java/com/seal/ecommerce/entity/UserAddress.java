package com.seal.ecommerce.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.Instant;

@Getter
@Setter
@Builder
@Entity
@Table(name = "user_address")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_address_id_gen")
    @SequenceGenerator(name = "user_address_id_gen", sequenceName = "user_address_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
}