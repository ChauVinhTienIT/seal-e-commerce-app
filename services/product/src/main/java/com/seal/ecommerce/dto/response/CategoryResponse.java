package com.seal.ecommerce.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryResponse {
    private Integer id;
    private String name;
    private String image;
}
