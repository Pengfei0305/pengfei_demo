package com.pengfei.sell.controller;

import com.pengfei.sell.Enum.ResultEnum;
import com.pengfei.sell.Exception.SellException;
import com.pengfei.sell.Util.ResultVOUtil;
import com.pengfei.sell.VO.ResultVO;
import com.pengfei.sell.converter.OrderForm2OrderDTOConverter;
import com.pengfei.sell.dto.OrderDTO;
import com.pengfei.sell.form.OrderForm;
import com.pengfei.sell.service.OrderService;
import com.pengfei.sell.weChatConfig.WxAccount;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pengfei
 * @date 2019-10-20 15:21
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private WxAccount wxAccount;

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单参数不正确】： result = {}"
                    +bindingResult.getFieldError().getDefaultMessage());
            throw new SellException(ResultEnum.ORDER_VALID_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }

        log.info(wxAccount.getAppId());

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        OrderDTO result = orderService.createOrder(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);

    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openId")String openId,
                                            @RequestParam(value = "page",defaultValue = "0")Integer page,
                                            @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openId)){
            log.error("【订单列表查询】Openid is NULL");
            throw new SellException(ResultEnum.OPENID_NULL);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage =  orderService.getOrderList(openId,pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());

    }

    //订单详情

    @GetMapping("/orderDetail")
    public ResultVO<OrderDTO> detail(@RequestParam("openId")String openId,
                                     @RequestParam("orderId")String orderId){
        OrderDTO orderDTO = orderService.getOrder(orderId);

        return ResultVOUtil.success(orderDTO);

    }

    //取消订单

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openId")String openId,
                           @RequestParam("orderId")String orderId){

        OrderDTO orderDTO = orderService.getOrder(orderId);
        orderService.cancelOrder(orderDTO);

        return ResultVOUtil.success();

    }
}
