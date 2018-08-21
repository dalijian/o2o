package com.lijian.o2o.dao;

import com.lijian.o2o.entity.Product;
import com.lijian.o2o.entity.ProductImg;

import java.util.List;

public interface ProductDao {
    int addProduct(Product product);

    int addBatchProductImg(List<ProductImg> productImgList
    );
}
