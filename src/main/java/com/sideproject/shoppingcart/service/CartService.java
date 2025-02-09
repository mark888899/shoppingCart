package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.Cart;
import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.repository.CartRepository;
import com.sideproject.shoppingcart.repository.ProductRepository;
import com.sideproject.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> addToCart(Long userId, Long productId,int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(404).body("找不到該商品");
        }
        Product product = optionalProduct.get();

        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existingCartItem.isPresent()) {
            // 購物車內已有該商品，則更新數量
            Cart cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + quantity); // 累加
            cartRepository.save(cart);
            return ResponseEntity.ok("已更新购物车中的商品数量: " + cart.getQuantity());
        } else {
            // 沒有該商品，新增項目到購物車
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(product.getId());
            cart.setProductName(product.getName());
            cart.setPrice(product.getPrice());
            cart.setQuantity(quantity);
            cartRepository.save(cart);
            return ResponseEntity.ok("新商品已添加到购物车，用户ID: " + userId + "，商品ID: " + productId);
        }
    }

    public ResponseEntity<?> getCartItems(Long userId) {
        List<Cart> userCartItems = cartRepository.findByUserId(userId);
        return ResponseEntity.ok(userCartItems);
    }

    public boolean removeFromCart(Long userId, Long id) {
        // 確認該商品是否存在於用戶的購物車
        Optional<Cart> cartItem = cartRepository.findByUserIdAndId(userId, id);

        if (cartItem.isPresent()) {
            cartRepository.delete(cartItem.get());
            return true;
        }
        return false;
    }
}