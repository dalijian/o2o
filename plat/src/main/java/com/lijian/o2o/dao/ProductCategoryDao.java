package com.lijian.o2o.dao;

import com.lijian.o2o.entity.ProductCategory;
import com.lijian.o2o.entity.Shop;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategory(@Param("shop")Shop shop);

    List<ProductCategory> queryProductCategoryByShopId(Long shopId);

    int delProductCategory(Long productCategoryId);

    int batchInsertProductCategory(List list);
   int deleteProductCategory(@Param("shopId")Long shopId,@Param("productCategoryId")Long productCategoryId);

}
