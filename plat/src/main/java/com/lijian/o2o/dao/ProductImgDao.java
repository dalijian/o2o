package com.lijian.o2o.dao;

import com.lijian.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
     int batchInsertProductImg(List<ProductImg> productImgList) ;

     int deleteProductImgByProductId(Long id);

    int addBatchProductImg(List<ProductImg> productImgList);
}
