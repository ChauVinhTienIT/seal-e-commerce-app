package com.seal.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "colors")
public class Color {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "color_gen")
    @SequenceGenerator(name = "color_gen", sequenceName = "color_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

}