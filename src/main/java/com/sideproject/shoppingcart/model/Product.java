package com.sideproject.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock; // 庫存數量

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category; // 產品類別關聯
}
