package com.lijian.o2o.service;

import java.io.InputStream;

import com.lijian.o2o.dto.ShopExecution;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.util.ImageHolder;

public interface ShopService  {
	ShopExecution addShop(Shop shop, ImageHolder imageHolder);

	Shop getByShopId(Long id);

	ShopExecution modifyShop(Shop shop, ImageHolder imageHolder);

	/**
	 * 根据分页返回数据
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

}
