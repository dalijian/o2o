package com.lijian.o2o.service;

import java.io.InputStream;

import com.lijian.o2o.dto.ShopExecution;
import com.lijian.o2o.entity.Shop;

public interface ShopService  {
	ShopExecution addShop(Shop shop, InputStream input, String fileName);

	Shop getByShopId(Long id);

	ShopExecution modifyShop(Shop shop, InputStream inputStream, String fileName);

	/**
	 * 根据分页返回数据
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

}
