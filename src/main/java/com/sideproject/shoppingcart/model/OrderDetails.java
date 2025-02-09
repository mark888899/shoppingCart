package com.sideproject.shoppingcart.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orderdetails")
@Data
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 訂單詳情 ID，自動遞增

    @JoinColumn(name = "order_id", nullable = false)
    private Long orderId; // 關聯的訂單

    @Column(nullable = false)
    private Long productId; // 商品名稱

    @Column(nullable = false)
    private String productName; // 商品名稱

    @Column(nullable = false)
    private int quantity; // 購買數量

    @Column(nullable = false)
    private double price; // 單價

}
