package com.lijian.o2o.service;

import com.lijian.o2o.dto.ProductExecution;
import com.lijian.o2o.entity.Product;
import com.lijian.o2o.exception.ProductOperationException;
import com.lijian.o2o.util.ImageHolder;

import java.util.List;

public interface ProductService {
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> thumbnailList) throws ProductOperationException;

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> thumbnailList) throws ProductOperationException;

    Product getProductById(Long productId);



    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

}
