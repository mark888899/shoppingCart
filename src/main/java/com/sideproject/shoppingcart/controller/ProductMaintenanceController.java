package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.service.ProductMaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/Maintenance")
public class ProductMaintenanceController {

    @Autowired
    private ProductMaintenanceService productMaintenanceService;

    @PostMapping("/add")
    @Operation(summary = "新增商品", description = "新增商品")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return productMaintenanceService.addProduct(product); //TODO 前端上傳照片時的處理
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品", description = "更新商品")
    public ResponseEntity<Product> updateProduct(Product product) {
        return productMaintenanceService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除商品", description = "刪除商品")
    public ResponseEntity<?> deleteProduct(@Parameter(description = "商品 ID", required = true, example = "1")
                                               @PathVariable("id")Long id) {
        return productMaintenanceService.deleteProduct(id);
    }

}
