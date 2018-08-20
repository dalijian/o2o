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
		List<ShopCategory> shopCategoryList = shopcategoryDao.queryShopCategory(new ShopCategory());
		Assert.assertEquals(21, shopCategoryList.size());
	}

}
