package com.sideproject.shoppingcart.repository;

import com.sideproject.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    String findUsernameById(Long id);

    Optional<User> findByUserEmail(String email);
}
