package com.lijian.o2o.dao;

import com.lijian.o2o.entity.Product;
import com.lijian.o2o.entity.ProductCategory;
import com.lijian.o2o.entity.ProductImg;
import com.lijian.o2o.entity.Shop;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testAddBatchProductImg() {
        ProductImg productImg_1 = new ProductImg();
        productImg_1.setCreateTime(new Date());
        productImg_1.setImgAddr("F:/");
        productImg_1.setImgDesc("lijian of picture_1");
        productImg_1.setProductId(12L);
        ProductImg productImg_2 = new ProductImg();
        productImg_2.setCreateTime(new Date());
        productImg_2.setImgAddr("F:/");
        productImg_2.setImgDesc("lijian of picture_2");
        productImg_2.setProductId(12L);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg_1);
        productImgList.add(productImg_2);
        int effectedNum = productDao.addBatchProductImg(productImgList);
        Assert.assertEquals(2, effectedNum);

    }
    @Test
    public void testAddProduct(){
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setImgAddr("F:/");
        product.setLastEditTime(new Date());

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(17L);
        product.setProductCategory(productCategory);


        Shop shop= new Shop();
        shop.setShopId(79L);
       product.setShop(shop);

        product.setProductDesc("可口可乐");
       product.setProductName("可口可乐");
       product.setPriority(10);
       product.setPromotionPrice("2.5");
       product.setNormalPrice("3.0");

      int effectedNum = productDao.addProduct(product);
        Assert.assertEquals(1, effectedNum);




    }
}
