package com.pengfei.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pengfei.sell.Enum.ResultEnum;
import com.pengfei.sell.Exception.SellException;
import com.pengfei.sell.dataobject.OrderDetail;
import com.pengfei.sell.dto.OrderDTO;
import com.pengfei.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pengfei
 * @date 2019-10-20 15:37
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){

        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),
                                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch(Exception e){
            log.error("【对象转换】String = {}",orderForm.getItems());
            throw new SellException(ResultEnum.ORDER_VALID_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;

    }

}
