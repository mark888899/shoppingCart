package com.sideproject.shoppingcart.dto;

import lombok.Data;

@Data
public class CartRequest {

    private Long productId; // 商品Id
    private int quantity; // 數量


}
