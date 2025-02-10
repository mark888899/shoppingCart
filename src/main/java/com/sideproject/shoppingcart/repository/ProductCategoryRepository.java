package com.sideproject.shoppingcart.repository;

import com.sideproject.shoppingcart.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
