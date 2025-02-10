package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.ProductCategory;
import com.sideproject.shoppingcart.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    public List<ProductCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ProductCategory addCategory(ProductCategory category) {
        return categoryRepository.save(category);
    }
}
