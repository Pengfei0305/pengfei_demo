package com.pengfei.sell.dataobject;

import com.pengfei.sell.Enum.OrderStatusEnum;
import com.pengfei.sell.Enum.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Pengfei
 * @date 2019-10-15 20:24
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    private Integer payStatus = PayStatusEnum.NEW.getCode();

    private Date createTime;

    private Date updateTime;

}
