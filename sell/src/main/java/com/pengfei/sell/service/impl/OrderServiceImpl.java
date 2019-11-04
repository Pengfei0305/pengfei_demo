package com.pengfei.sell.service.impl;

import com.pengfei.sell.Enum.OrderStatusEnum;
import com.pengfei.sell.Enum.PayStatusEnum;
import com.pengfei.sell.Enum.ResultEnum;
import com.pengfei.sell.Exception.SellException;
import com.pengfei.sell.Util.KeyUtil;
import com.pengfei.sell.Util.SystemUtil;
import com.pengfei.sell.converter.OrderMaster2OrderDTOConverter;
import com.pengfei.sell.dataobject.OrderDetail;
import com.pengfei.sell.dataobject.OrderMaster;
import com.pengfei.sell.dataobject.ProductInfo;
import com.pengfei.sell.dto.CartDTO;
import com.pengfei.sell.dto.OrderDTO;
import com.pengfei.sell.repository.OrderDetailRepository;
import com.pengfei.sell.repository.OrderMasterRepository;
import com.pengfei.sell.service.OrderService;
import com.pengfei.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pengfei
 * @date 2019-10-19 14:39
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;


    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);

        //1. 查询商品
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3. 写入订单数据库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetailRepository.save(orderDetail);

        }
        //3. 写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.NEW.getCode());
        try {
            orderMaster.setCreateTime(SystemUtil.getTime());
            orderMaster.setUpdateTime(SystemUtil.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderMasterRepository.save(orderMaster);


        //4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);


        return orderDTO;
    }

    @Override
    public OrderDTO getOrder(String id) {
        OrderMaster orderMaster = orderMasterRepository.findOne(id);
        if(orderMaster==null){
            throw new SellException(ResultEnum.PRODUCT_ORDER_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(id);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> getOrderList(String openid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(openid,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancelOrder(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单状态不正确】，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result==null){
            log.error("【取消订单失败】result={}"+result.toString());
            throw new SellException(ResultEnum.ORDER_CANCEL_ERROR);
        }

        //返还库存
        if(orderDTO.getOrderDetailList().isEmpty()){
            log.error("【订单中无商品】result={}"+orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //若已支付，退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO payOrder(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【修改支付状态，订单状态不正确】，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.NEW.getCode())){
            log.error("【修改支付状态，订单支付状态不正确】，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result==null){
            log.error("【支付更新失败】result={}"+result.toString());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finishOrder(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单订单状态不正确】，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result==null){
            log.error("【完结订单修改订单状态失败】result={}"+result.toString());
            throw new SellException(ResultEnum.ORDER_FINISH_ERROR);
        }
        return orderDTO;
    }
}
