package com.seal.ecommerce.repository;

import com.seal.ecommerce.entity.Category;
import com.seal.ecommerce.entity.SubCategory;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    List<SubCategory> findAllByCategoryId(Integer category_id);
}
