package com.lijian.o2o.service;

import java.io.File;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.liijian.o2o.enums.ShopStateEnum;
import com.lijian.o2o.dao.BaseTest;
import com.lijian.o2o.dto.ShopExecution;
import com.lijian.o2o.entity.Area;
import com.lijian.o2o.entity.PersonInfo;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.entity.ShopCategory;

import junit.framework.Assert;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	
	@Test
	public void addShopTest(){
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(30L);
		
		shop.setOwner(owner);
		shop.setShopCategory(shopCategory);
		shop.setArea(area);
		shop.setCreateTime(new Date());
		shop.setAdvice("审核中");
		shop.setShopName("测试的店铺-11111");
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		
		shop.setLastEditTime(new Date());
		shop.setShopDesc("test");
		shop.setPhone("test");
		shop.setShopAddr("test");
		
		File shopImg = new File("src/test/resources/小黄人_1.jpeg");
		ShopExecution se = shopService.addShop(shop, shopImg);
//		Long shopId = shop.getShopId();
//		System.out.println("shopId-->"+shopId);
		Assert.assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
//		Assert.assertEquals(1, effectedNum);
	}

}
