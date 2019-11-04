package com.pengfei.sell.service;

import com.pengfei.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * @author Pengfei
 * @date 2019-10-13 20:35
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
