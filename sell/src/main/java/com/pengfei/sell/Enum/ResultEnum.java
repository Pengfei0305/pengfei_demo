package com.pengfei.sell.Enum;

import lombok.Getter;

/**
 * @author Pengfei
 * @date 2019-10-19 14:46
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(10,"product_not_exist"),
    PRODUCT_STOCK_ERROR(11,"OUT OF STOCK"),
    PRODUCT_ORDER_EXIST(12,"order_not_exist"),
    ORDER_DETAIL_NOT_EXIST(13,"order_detail_not_exist"),
    ORDER_STATUS_ERROR(14,"order_status_error"),
    ORDER_PAY_STATUS_ERROR(19,"order_payStatus_error"),
    ORDER_CANCEL_ERROR(15,"order_cancel_error"),
    ORDER_DETAIL_EMPTY(16,"order_detail_empty"),
    ORDER_FINISH_ERROR(17,"order_finish_error"),
    ORDER_VALID_ERROR(18,"order_valid_error"),
    OPENID_NULL(18,"openId is null"),
    ;
    private Integer code;

    private  String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
