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
        Cart cart = new Cart();

        cart.setUserId(userId); // 綁定購物車項目到登入的使用者
        cart.setProductId(product.getId());
        cart.setProductName(product.getName());
        cart.setPrice(product.getPrice());
        cart.setQuantity(quantity);

        cartRepository.save(cart);
        return ResponseEntity.ok("Item added to cart for user ID: " + userId + " and product ID: " + productId);
    }

    public ResponseEntity<?> getCartItems(Long userId) {
        List<Cart> userCartItems = cartRepository.findByUserId(userId);
        return ResponseEntity.ok(userCartItems);
    }

    public ResponseEntity<?> removeFromCart(Long userId, Long productId) {
        Cart cartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
            return ResponseEntity.ok("用戶" + userRepository.findUsernameById(userId) + " 購物車中的 " + cartItem.getProductName() +" 商品已移除");
        }
        return ResponseEntity.status(404).body("Item not found in cart");
    }
}