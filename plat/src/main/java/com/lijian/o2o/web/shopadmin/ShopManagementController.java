package com.lijian.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lijian.o2o.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.liijian.o2o.enums.ShopStateEnum;
import com.lijian.o2o.dto.ShopExecution;
import com.lijian.o2o.entity.Area;
import com.lijian.o2o.entity.PersonInfo;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.entity.ShopCategory;
import com.lijian.o2o.service.AreaService;
import com.lijian.o2o.service.ShopCategoryService;
import com.lijian.o2o.service.ShopService;
import com.lijian.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private ShopCategoryService shopCategoryService;

	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {

		/*
		 * modelMap 返回的封装数据
		 */
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());

		}
		return modelMap;

	}

	/**
	 * 注册店铺
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		// 返回注册信息结果 给前端
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success",false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;

		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// 通过Gson 将前端传递的json 转换成shop 实例
			shop = new Gson().fromJson(shopStr, Shop.class);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 将上传文件的文件流 转换成 CommonsMultipartFile
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}

		if (shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);

			// 添加店铺
			ShopExecution se;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());

				// 检查返回值
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					return modelMap;
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
					return modelMap;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;

		}
	
	}

	/**
	 * 将输入流转换成 File 方便 service层 测试
	 * 
	 * @param ins
	 * @param file
	 */

}
