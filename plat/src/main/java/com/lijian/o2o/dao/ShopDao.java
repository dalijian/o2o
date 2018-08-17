package com.lijian.o2o.dao;

import com.lijian.o2o.entity.Shop;

public interface ShopDao {
	int insertShop(Shop shop);

	int updateShop(Shop shop);

	Shop queryByShopId(Long id);
}
