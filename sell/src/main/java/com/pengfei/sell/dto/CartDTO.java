package com.pengfei.sell.dto;

import lombok.Data;

/**
 * @author Pengfei
 * @date 2019-10-19 15:14
 */
@Data
public class CartDTO {
    private String productId;

    private  Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
