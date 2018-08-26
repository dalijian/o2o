package com.lijian.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.lijian.o2o.util.ImageHolder;
import com.lijian.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liijian.o2o.enums.ShopStateEnum;
import com.lijian.o2o.dao.ShopDao;
import com.lijian.o2o.dto.ShopExecution;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.exception.ShopOperationException;
import com.lijian.o2o.service.ShopService;
import com.lijian.o2o.util.ImageUtil;
import com.lijian.o2o.util.PathUtil;
@Service

public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop,ImageHolder imageHolder) {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);// TODO
		}
		try {
			//设置shop初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			shop.setAdvice("审核中");
			//添加shop
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (imageHolder.getImage() != null) { //图片等于空的话 没有判断
					// 存储图片
					try {
						//修改shop图片地址
						addshopImgInput(shop, imageHolder);
					} catch (Exception e) {
						throw new ShopOperationException("addshopImgInput error:" + e.getMessage());
					}
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addshop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
		

	}

	@Override
	public Shop getByShopId(Long id) {
		return shopDao.queryByShopId(id);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {
		try {
			//判断是否需要处理图片
			if (shop == null || shop.getShopId() == null && imageHolder.getImageName() != null && "".equals(imageHolder.getImageName())) {
				return new ShopExecution(ShopStateEnum.NULL_SHOP);


			} else {
				if (imageHolder.getImage() != null) {
				//拿到shopId
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());

					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());

					}
					//修改shop缩略图地址
					addshopImgInput(shop, imageHolder);
				}
				//更新shop
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);


				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);

				}
			}
		} catch (ShopOperationException e) {
			throw new ShopOperationException("shop modify error");

		}




	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopListCount(shopCondition);
		ShopExecution se = new ShopExecution();

		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);

		}else{
			se.setState(ShopStateEnum.INNER_ERROR.getState());

		}
		return se;

	}

	private void addshopImgInput(Shop shop, ImageHolder imageHolder) {
		// 获取shop 图片目录的相对值路径
		String desc = PathUtil.getShopImagePath(shop.getShopId());
		//返回缩略   图片 相对路径
		String shopImgInputAddr = ImageUtil.generateThumbnail(imageHolder,desc);
		//设置图片地址
		shop.setShopImg(shopImgInputAddr);

	}

}
