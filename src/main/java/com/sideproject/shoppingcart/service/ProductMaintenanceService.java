package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductMaintenanceService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 新增商品
     */
    public ResponseEntity<Product> addProduct(Product product) {
        if (product.getId() != null) {
            return ResponseEntity.badRequest().body(null); // 禁止傳入 id
        }
        if (product.getName() == null || product.getPrice() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    /**
     * 更新商品
     */
    public ResponseEntity<Product> updateProduct(Product product) {
        if (product.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isPresent()) {
            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 刪除商品
     */
    public ResponseEntity<?> deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("商品刪除成功");
        }
        return ResponseEntity.notFound().build();
    }
}
