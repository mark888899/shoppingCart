package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.model.ProductCategory;
import com.sideproject.shoppingcart.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        return ResponseEntity.ok(productCategoryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<ProductCategory> addCategory(@RequestBody ProductCategory productCategory) {
        return ResponseEntity.ok(productCategoryService.addCategory(productCategory));
    }
}
