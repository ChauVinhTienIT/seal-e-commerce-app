package com.seal.ecommerce.dto.response;

import com.seal.ecommerce.entity.SubCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductResponse {
    private Integer id;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String mainImage;
    private String brand;
    private Integer subCategory;
    private Double averageRating;
    private Integer reviewCount;
}