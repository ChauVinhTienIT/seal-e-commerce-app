package com.seal.ecommerce.repository;

import com.seal.ecommerce.entity.Inventory;
import com.seal.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<List<Inventory>> findByProductId(Integer productId);
    Page<Inventory> findAll(Pageable pageable);
}
