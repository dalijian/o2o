package com.lijian.o2o.web.shopadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin",method={RequestMethod.GET})
public class ShopAdminController {
	
	Logger logger = LoggerFactory.getLogger(ShopAdminController.class);
	
	
	@RequestMapping(value="/shopoperation")
	public String shopOperation(){
		return "shop/oparetion";  // 如果 html 命名为index.html 访问不到
		
	}
	@RequestMapping(value="/shoplist")
	public String shopList(){
		return "shop/shoplist"; 
		
	}
	@RequestMapping(value="/shopmanagement")
	public String shopManagement(){
		return "shop/shopmanagement"; 
		
	}

}
