package com.pengfei.sell.converter;

import com.pengfei.sell.dataobject.OrderMaster;
import com.pengfei.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pengfei
 * @date 2019-10-19 16:14
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;

    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList = orderMasterList.stream().map(e ->
                convert(e)
                ).collect(Collectors.toList());
        return orderDTOList;

    }
}
