package com.pengfei.sell.Enum;

import lombok.Data;
import lombok.Getter;

/**
 * @author Pengfei
 * @date 2019-10-13 21:15
 */
@Getter
public enum ProductStatusEnum {

    UP(0, "在架"),
    DOWN(1,"下架")
    ;

    private Integer code;

    private  String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
