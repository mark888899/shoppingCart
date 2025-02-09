package com.sideproject.shoppingcart.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 訂單 ID

    @Column(nullable = false)
    private Long userId; // 訂購人 ID

    @Column(nullable = false)
    private double totalAmount; // 訂單總金額

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate = new Date();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails; // 訂單內的商品
}
