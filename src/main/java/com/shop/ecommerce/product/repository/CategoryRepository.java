package com.shop.ecommerce.product.repository;

import com.shop.ecommerce.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
