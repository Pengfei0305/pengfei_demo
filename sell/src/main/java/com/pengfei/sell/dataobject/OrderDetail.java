package com.pengfei.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Pengfei
 * @date 2019-10-15 20:20
 */
@Entity
@Data
public class OrderDetail {

    /**  */
    @Id
    private String detailId;


    private String orderId;


    private String productId;


    private String productName;


    private BigDecimal productPrice;


    private  Integer productQuantity;


    private String productIcon;

}
