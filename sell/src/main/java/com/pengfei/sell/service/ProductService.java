package com.pengfei.sell.service;

import com.pengfei.sell.dataobject.ProductInfo;
import com.pengfei.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Pengfei
 * @date 2019-10-13 21:10
 */
public interface ProductService {
    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);

    List<ProductInfo> findUpAll();

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTOList);

    void decreaseStock(List<CartDTO> cartDTOList);


}
