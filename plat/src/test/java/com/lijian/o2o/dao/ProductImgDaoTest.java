package com.lijian.o2o.dao;

import com.lijian.o2o.entity.ProductImg;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductImgDaoTest extends BaseTest{
    @Autowired
    private ProductImgDao productImgDao;

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
        int effectedNum = productImgDao.addBatchProductImg(productImgList);
        Assert.assertEquals(2, effectedNum);

    }
}
