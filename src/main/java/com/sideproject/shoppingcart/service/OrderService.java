package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.dto.OrderList;
import com.sideproject.shoppingcart.model.Cart;
import com.sideproject.shoppingcart.model.Order;
import com.sideproject.shoppingcart.model.OrderDetails;
import com.sideproject.shoppingcart.repository.CartRepository;
import com.sideproject.shoppingcart.repository.OrderDetailsRepository;
import com.sideproject.shoppingcart.repository.OrderRepository;
import com.sideproject.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> checkout(Long userId) {
        // 1. 取得購物車內所有商品
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("購物車內沒有商品");
        }

        // 2. 計算訂單總金額
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();

        // 3. 建立訂單
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        order.setOrderStatus(0); // 待出貨
        final Order savedOrder = orderRepository.save(order); // 儲存訂單，獲取 ID

        // 4. 轉換購物車商品為訂單商品
        List<OrderDetails> orderDetailsList = cartItems.stream()
                .map(cartItem -> {
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setOrderId(savedOrder.getId());
                    orderDetails.setProductId(cartItem.getProductId());
                    orderDetails.setProductName(cartItem.getProductName());
                    orderDetails.setQuantity(cartItem.getQuantity());
                    orderDetails.setPrice(cartItem.getPrice());
                    return orderDetails;
                }).collect(Collectors.toList());

        // 5. 儲存訂單明細
        orderDetailsRepository.saveAll(orderDetailsList);

        // 6. 清空購物車
        cartRepository.deleteAll(cartItems);

        return ResponseEntity.ok(order);
    }

    public ResponseEntity<?> getOrder(Long userId) {
        List<OrderList> orderDetails = new ArrayList<>();

        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(404).body("該用戶查無訂單");
        } else {
            orders.forEach(order -> {
                OrderList orderList = new OrderList();
                orderList.setOrderId(order.getId()); // 設置訂單 ID
                orderList.setUserId(order.getUserId()); // 設置用戶 ID
                orderList.setTotalPrice(order.getTotalPrice()); // 設置總價格
                orderList.setOrderDate(order.getOrderDate()); // 設置訂單日期
                orderList.setOrderStatus(order.getOrderStatus()); // 訂單狀況

                // 查詢訂單細節並放入 orderDetails
                List<OrderDetails> details = orderDetailsRepository.findByOrderId(order.getId());
                orderList.setOrderDetails(details);

                orderDetails.add(orderList);
            });
            return ResponseEntity.ok(orderDetails);
        }
    }
}
