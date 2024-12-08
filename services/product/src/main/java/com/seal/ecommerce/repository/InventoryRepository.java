package com.seal.ecommerce.repository;

import com.seal.ecommerce.entity.Inventory;
import com.seal.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByProduct(Product product);
}
