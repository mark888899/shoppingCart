package com.sideproject.shoppingcart.dto;

import com.sideproject.shoppingcart.model.OrderDetails;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderList {


    private Long orderId; // 訂單 ID

    private Long userId; // 訂購人 ID

    private double totalPrice; // 訂單總金額

    private Date orderDate;

    private int orderStatus;

    private List<OrderDetails> orderDetails;

}
