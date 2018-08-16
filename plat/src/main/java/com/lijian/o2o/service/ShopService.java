package com.lijian.o2o.service;

import java.io.InputStream;

import com.lijian.o2o.dto.ShopExecution;
import com.lijian.o2o.entity.Shop;

public interface ShopService {
	ShopExecution addShop(Shop shop, InputStream input, String fileName);

}
