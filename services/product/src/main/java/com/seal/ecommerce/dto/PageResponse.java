package com.seal.ecommerce.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    long totalPages;
    long pageSize;
    long totalElement;
    long currentPage;

    @Builder.Default
    List<T> data = Collections.emptyList();

}
