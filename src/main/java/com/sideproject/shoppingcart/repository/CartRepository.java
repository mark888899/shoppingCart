package com.sideproject.shoppingcart.repository;

import com.sideproject.shoppingcart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);

    Optional<Cart> findByUserIdAndId(Long userId, Long id);

    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);
}
