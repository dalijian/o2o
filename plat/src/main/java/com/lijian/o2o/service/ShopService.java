package com.lijian.o2o.service;

import java.io.File;

import com.lijian.o2o.dto.ShopExecution;
import com.lijian.o2o.entity.Shop;

public interface ShopService {
ShopExecution addShop(Shop shop,File shopImg);

}
