package com.lijian.o2o.service;

import com.lijian.o2o.enums.ProductStateEnum;
import com.lijian.o2o.dao.BaseTest;
import com.lijian.o2o.dto.ProductExecution;
import com.lijian.o2o.entity.*;
import com.lijian.o2o.util.ImageHolder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceTest  extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Shop shop =new Shop();
        shop.setShopId(79L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(17L);

        Product product = new Product();
        product.setProductName("商品");

        product.setShop(shop);
        product.setPriority(100);
        product.setProductCategory(productCategory);

        InputStream imgInputStream = new FileInputStream(new File("src/test/resources/Lighthouse.jpg"));


        InputStream imgInputStream_img_1 = new FileInputStream(new File("src/test/resources/Penguins.jpg"));

        InputStream imgInputStream_img_2 = new FileInputStream(new File("src/test/resources/Koala.jpg"));

        ImageHolder imageHolder = new ImageHolder("Lighthouse.jpg",imgInputStream);
        ImageHolder imageHolder_img_1 = new ImageHolder("Penguins.jpg",imgInputStream_img_1);
        ImageHolder imageHolder_img_2 = new ImageHolder("Koala.jpg",imgInputStream_img_2);

        List<ImageHolder> imageHolderList = new ArrayList<>();
        imageHolderList.add(imageHolder_img_1);
        imageHolderList.add(imageHolder_img_2);

       ProductExecution productExecution = productService.addProduct(product, imageHolder, imageHolderList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());


    }
    @Test
    public void testModifyProduct() throws FileNotFoundException {

//        1. 先查询个product 在 修改 应为 要修改 详细图 是根据 productID 到 product_img 中拿到 详细图地址
// 不用可以设置的 productID


        Shop shop =new Shop();
        shop.setShopId(79L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(17L);

        Product product = new Product();
        product.setProductId(14L);
        product.setProductName("雪碧");

        product.setShop(shop);
        product.setPriority(100);
        product.setProductCategory(productCategory);

        InputStream imgInputStream = new FileInputStream(new File("src/test/resources/Lighthouse.jpg"));


        InputStream imgInputStream_img_1 = new FileInputStream(new File("src/test/resources/Penguins.jpg"));

        InputStream imgInputStream_img_2 = new FileInputStream(new File("src/test/resources/Koala.jpg"));

        ImageHolder imageHolder = new ImageHolder("Lighthouse.jpg",imgInputStream);
        ImageHolder imageHolder_img_1 = new ImageHolder("Penguins.jpg",imgInputStream_img_1);
        ImageHolder imageHolder_img_2 = new ImageHolder("Koala.jpg",imgInputStream_img_2);

        List<ImageHolder> imageHolderList = new ArrayList<>();
        imageHolderList.add(imageHolder_img_1);
        imageHolderList.add(imageHolder_img_2);

        ProductExecution productExecution = productService.modifyProduct(product, imageHolder, imageHolderList);
        for (ProductImg img:product.getProductImgList()){
            logger.info(img.getImgAddr());
        }
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());



    }
}
