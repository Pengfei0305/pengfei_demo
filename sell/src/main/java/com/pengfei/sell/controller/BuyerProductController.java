package com.pengfei.sell.controller;

import com.pengfei.sell.Util.ResultVOUtil;
import com.pengfei.sell.VO.ProductInfoVO;
import com.pengfei.sell.VO.ProductVO;
import com.pengfei.sell.VO.ResultVO;
import com.pengfei.sell.dataobject.ProductCategory;
import com.pengfei.sell.dataobject.ProductInfo;
import com.pengfei.sell.service.CategoryService;
import com.pengfei.sell.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pengfei
 * @date 2019-10-13 21:42
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){

        //1. get all the 0 status products

        List<ProductInfo> productInfoList = productService.findUpAll();

        //2. get category

        List<Integer> categoryList = new ArrayList<>();
        //传统方法
        /*for(ProductInfo productInfo : productInfoList){
            categoryList.add(productInfo.getCategoryType());
        }*/
        //Java 8 (lambda)
        categoryList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        //System.out.println(categoryList.toString());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryList);

        //3. data
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //System.out.println(productInfo.getProductId());
                    BeanUtils.copyProperties(productInfo,productInfoVO);//copy  data
                    //System.out.println(productInfoVO.getProductId());
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        return ResultVOUtil.success(productVOList);

    }
}
