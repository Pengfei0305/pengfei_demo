package com.pengfei.sell.Enum;

import lombok.Getter;

/**
 * @author Pengfei
 * @date 2019-10-15 20:26
 */
@Getter
public enum OrderStatusEnum {
    NEW(0 , "new order"),
    SUCCESS(1 , "finished"),
    CANCEL(2 , "cancel"),
    DELIVER(3 , "deliver"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
