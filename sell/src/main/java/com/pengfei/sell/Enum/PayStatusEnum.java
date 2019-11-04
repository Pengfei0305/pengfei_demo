package com.pengfei.sell.Enum;

import lombok.Getter;

/**
 * @author Pengfei
 * @date 2019-10-15 20:26
 */
@Getter
public enum PayStatusEnum {
    NEW(0 , "not pay"),
    SUCCESS(1 , "payed"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
