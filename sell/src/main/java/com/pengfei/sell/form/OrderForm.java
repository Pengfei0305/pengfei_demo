package com.pengfei.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Pengfei
 * @date 2019-10-20 15:25
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "手机不能为空")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "openId不能为空")
    private String openId;

    @NotEmpty(message = "购物车不能为空")
    private String items;

}
