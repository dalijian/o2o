package com.lijian.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lijian.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 添加店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
/**
 * 更新店铺
 * @param shop
 * @return
 */
	int updateShop(Shop shop);
/**
 * 根据店铺iD查询店铺
 * @param id
 * @return
 */
	Shop queryByShopId(Long id);
	
	/**
	 * 分页查询店铺， 根据 店铺名，店铺状态，店铺类别，区域Id，owner
	 * @param shopCondition shop对象
	 * @param rowIndex		开始索引
	 * @param pageSize		每页  页面  记录数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);
	/**
	 * 返回queryShopList总数
	 * @param shopCondition shop对象
	 * @return
	 */
	int queryShopListCount(@Param("shopCondition")Shop shopCondition);

}
