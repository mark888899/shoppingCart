package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ProductMaintenanceService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 新增商品
     */
    public ResponseEntity<?> addProduct(Product product) {
        if (product.getId() != null) {
            return ResponseEntity.status(404).body(Map.of("message", "ID為系統產生，不可自行輸入")); // 禁止傳入 id
        }
        if (product.getName() == null || product.getPrice() == 0) {
            return ResponseEntity.status(404).body(Map.of("message", "請輸入正確的商品資訊"));
        }
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    /**
     * 更新商品
     */
    public ResponseEntity<?> updateProduct(Product product) {
        if (product.getId() == null) {
            return ResponseEntity.status(404).body(Map.of("message", "系統出錯，請通知客服"));
        }

        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isPresent()) {
            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.status(404).body(Map.of("message", "商品不存在"));
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
        return ResponseEntity.status(404).body(Map.of("message", "商品不存在"));
    }
}
