package com.lijian.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lijian.o2o.entity.Area;
import com.lijian.o2o.entity.PersonInfo;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.entity.ShopCategory;

import junit.framework.Assert;

public class ShopDaoTest extends BaseTest {

	@Autowired
	private ShopDao shopDao;

	/**
	 * 
	 */
	@Test
	public void insertShopTest() {
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
		shop.setShopName("lijain的店铺");
		shop.setEnableStatus(1);
		shop.setLastEditTime(new Date());
		shop.setShopDesc("test");
		shop.setPhone("test");
		shop.setShopAddr("test");
		int effectedNum = shopDao.insertShop(shop); //拿到影响行数
		Long shopId = shop.getShopId();
		System.out.println("shopId-->"+shopId);
		Assert.assertEquals(1, effectedNum);

	}
	
	@Test
	public void updateShopTest() {
		Shop shop = new Shop();
		shop.setShopId(38L);
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
		shop.setShopName("lijian的店铺");
		shop.setEnableStatus(1);
		shop.setLastEditTime(new Date());
		shop.setShopDesc("test");
		shop.setPhone("test");
		shop.setShopAddr("test");
		int effectedNum = shopDao.updateShop(shop); //拿到影响行数
		Long shopId = shop.getShopId();  //拿到update 更新主键值
		System.out.println("shopId-->"+shopId);
		Assert.assertEquals(1, effectedNum);

	}
	@Test
	public void QueryShopByIdTest(){

		Shop shop = shopDao.queryByShopId(79L);
		System.out.println("shop-->"+shop);
		int areaId = shop.getArea().getAreaId();
		Long shopCategoryId = shop.getShopCategory().getShopCategoryId();
		
		/*Assert.assertEquals(3,areaId);
		Assert.assertEquals(java.util.Optional.of(14L).get(), shopCategoryId);*/


	}
	@Test
	public void queryShopListAndCountTest(){
		Shop shop = new Shop();

		PersonInfo user = new PersonInfo();
		user.setUserId(1L);

		List<Shop> shopList =shopDao.queryShopList(shop, 0, 5);
		System.out.println(shopList);
		int count = shopDao.queryShopListCount(shop);
		System.out.println(count);

	}



}
