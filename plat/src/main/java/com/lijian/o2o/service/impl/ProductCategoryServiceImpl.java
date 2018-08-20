package com.lijian.o2o.service.impl;

import com.liijian.o2o.enums.ProductCategoryStateEnum;
import com.lijian.o2o.dao.ProductCategoryDao;
import com.lijian.o2o.dao.ShopCategoryDao;
import com.lijian.o2o.dto.ProductCategoryExecution;
import com.lijian.o2o.entity.ProductCategory;
import com.lijian.o2o.entity.ShopCategory;
import com.lijian.o2o.exception.ProductCategoryOperationException;
import com.lijian.o2o.service.ProductCategoryService;
import com.lijian.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;


	@Override
	public List<ProductCategory> getProductCategoryList(ShopCategory shopCategoryCondition) {
		return null;
	}

	@Override
	public List<ProductCategory> getProductCategoryListByShopId(Long shopId) {
		return productCategoryDao.queryProductCategoryByShopId(shopId);
	}

	@Override
	public int delProductCategory(Long productCategoryId) {
		return productCategoryDao.delProductCategory(productCategoryId);
	}

	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new ProductCategoryOperationException("商品类别创建失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory Error:" + e.getMessage());
			}

		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(Long shopId, Long productCategoryId)throws ProductCategoryOperationException{
		//TODO 在删除商品类别 前 先要删除 该类别下的所有商品
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(shopId, productCategoryId);
			if (effectedNum <= 0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			} else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);

			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());

		}

	}
}
