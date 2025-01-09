package com.seal.ecommerce.repository;

import com.seal.ecommerce.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findAllByProductId(Integer productId);
}
