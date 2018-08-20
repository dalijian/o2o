package com.lijian.o2o.dao;

import com.lijian.o2o.entity.ProductCategory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//按照方法字母顺序执行 从而使测试形成闭环 ，最终不改变数据库数据
/*
 * 想利用 product_id 进行闭环 是无效的， 应为 mybatis 的 主键 如果设置 城 自增的话， 设置productId 是无效的，实际Id还是递增
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends  BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testqueryProductCategoryById(){
        List<ProductCategory> productCategoryDaoList=productCategoryDao.queryProductCategoryByShopId(28L);
        System.out.println(productCategoryDaoList.get(0).getProductCategoryId());
        Assert.assertNotNull(productCategoryDaoList);
        Assert.assertEquals(6,productCategoryDaoList.size());

    }
    @Test
    public void testBatchInsertProductCategory(){
        ProductCategory productCategory_1 = new ProductCategory();
        productCategory_1.setCreateTime(new Date());
        productCategory_1.setPriority(20);
        productCategory_1.setProductCategoryId(13L);
        productCategory_1.setProductCategoryName("鸡块");
        productCategory_1.setShopId(79L);
        ProductCategory productCategory_2 = new ProductCategory();
        productCategory_2.setCreateTime(new Date());
        productCategory_2.setPriority(21);
        productCategory_2.setProductCategoryId(10L);
        productCategory_2.setProductCategoryName("鸡腿");
        productCategory_2.setShopId(79L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory_1);
        productCategoryList.add(productCategory_2);
     int resultColumn =   productCategoryDao.batchInsertProductCategory(productCategoryList);
        System.out.println(resultColumn);


    }
    @Test
    public void testDeleteProductCategory(){
        List<ProductCategory> productCategoryDaoList=productCategoryDao.queryProductCategoryByShopId(79L);
        for(ProductCategory ps:productCategoryDaoList){
            if ("鸡腿".equals(ps.getProductCategoryName()) || "鸡块".equals(ps.getProductCategoryName())) {
                productCategoryDao.deleteProductCategory(79L, ps.getProductCategoryId());
            }
        }
        
    }
}
