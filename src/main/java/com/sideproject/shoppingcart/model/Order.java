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
    private double totalPrice; // 訂單總金額

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate = new Date();

    @Column(nullable = false)
    private int orderStatus; // 訂單狀況  0:待出貨 1:已出貨 2:訂單已完成 3:訂單取消


}
