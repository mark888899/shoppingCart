package com.sideproject.shoppingcart.repository;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockGreaterThan(int stock);

    List<Product> findByCategory(ProductCategory category);

}
