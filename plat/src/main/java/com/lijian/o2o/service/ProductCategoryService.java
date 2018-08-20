package com.lijian.o2o.service;

import com.lijian.o2o.dto.ProductCategoryExecution;
import com.lijian.o2o.entity.ProductCategory;
import com.lijian.o2o.entity.ShopCategory;
import com.lijian.o2o.exception.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
List<ProductCategory> getProductCategoryList(ShopCategory shopCategoryCondition);
List<ProductCategory> getProductCategoryListByShopId(Long shopId);

    int  delProductCategory(Long productCategoryId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    ProductCategoryExecution deleteProductCategory(Long shopId, Long productCategoryId) throws ProductCategoryOperationException;
}
