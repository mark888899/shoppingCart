package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> products = productRepository.findByStockGreaterThan(0);
        return ResponseEntity.ok(products);
    }
}
