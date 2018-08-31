package com.lijian.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lijian.o2o.entity.ShopCategory;

import junit.framework.Assert;

public class ShopCategoryDaoTest  extends BaseTest{
	@Autowired
	private ShopCategoryDao shopcategoryDao;
	
	@Test
	public void testQueryShopCatetory(){
		ShopCategory shopCategoryChild =new ShopCategory();
		ShopCategory shopCategoryParent = new ShopCategory();
		shopCategoryParent.setShopCategoryId(12L);
		shopCategoryChild.setParent(shopCategoryParent);
		List<ShopCategory> shopCategoryList = shopcategoryDao.queryShopCategory(shopCategoryChild);
		Assert.assertEquals(21, shopCategoryList.size());
	}

}
