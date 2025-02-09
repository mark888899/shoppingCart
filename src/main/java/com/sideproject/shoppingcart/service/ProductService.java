package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> products = productRepository.findByStockGreaterThan(0);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<?> getProductDetails(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok(product.get()); // 如果找到商品，回傳 200 OK
        } else {
            return ResponseEntity.status(401).body("找不到該商品"); // 如果找不到，回傳 401 + 錯誤訊息
        }
    }
}
