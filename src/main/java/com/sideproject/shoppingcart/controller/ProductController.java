package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/available")
    @Operation(summary = "商品列表", description = "在庫中的商品列表")
    public ResponseEntity<List<Product>> getAvailableProducts() {
        return productService.getAvailableProducts();
    }
}
