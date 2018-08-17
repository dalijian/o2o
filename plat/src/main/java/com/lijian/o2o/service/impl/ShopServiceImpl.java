package com.lijian.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;

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
	public ShopExecution addShop(Shop shop, InputStream shopImgInput,String fileName) {
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
				if (shopImgInput != null) { //图片等于空的话 没有判断
					// 存储图片
					try {
						//修改shop图片地址
						addshopImgInput(shop, shopImgInput,fileName);
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
	private void addshopImgInput(Shop shop, InputStream shopImgInput,String fileName) {
		// 获取shop 图片目录的相对值路径
		String desc = PathUtil.getShopImagePath(shop.getShopId());
		//返回缩略   图片 相对路径
		String shopImgInputAddr = ImageUtil.generateThumbnail(shopImgInput, desc,fileName);
		//设置图片地址
		shop.setShopImg(shopImgInputAddr);

	}

}
