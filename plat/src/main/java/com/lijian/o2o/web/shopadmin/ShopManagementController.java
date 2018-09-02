package com.lijian.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lijian.o2o.util.CodeUtil;
import com.lijian.o2o.util.ImageHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.lijian.o2o.enums.ShopStateEnum;
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

	/**
	 * 初始化店铺信息
	 * @return
	 */
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
			modelMap.put("success", false);
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
		// 添加店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");

			shop.setOwner(owner);

			ShopExecution se;
			try {
				se = shopService.modifyShop(shop, new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream()));

				// 检查返回值
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					
					//用户可以操作的店铺
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if(shopList==null||shopList.size()==0){
						shopList=new ArrayList<Shop>();
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList", shopList);
						
					}else{
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList", shopList);
						
					}
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
	 * 通过店铺Id 拿到 店铺信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("success", true);
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);

			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "shopId 不正确");
		}
		return modelMap;

	}

	/**
	 * 修改店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		// 返回注册信息结果 给前端
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;

		}
		// 通过Gson 将前端传递的json 转换成shop 实例
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {

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

		}

		// 修改店铺
		if (shop != null && shop.getShopId() != null) {

			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);

			ShopExecution se;
			try {
				if (shopImg != null) {
					se = shopService.modifyShop(shop, new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream()));
				} else {

					se = shopService.modifyShop(shop, new ImageHolder(null, null));
				}
				// 检查返回值
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
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
			modelMap.put("errMsg", "请输入店铺ID");
			return modelMap;

		}

	}
/*
    管理 shop信息

     */
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");

		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/platform/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		}
		else {
			
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			//设置 sessionAttribute currentShop
			request.getSession().setAttribute("currentShop", currentShop);
			
			modelMap.put("redirect", false);

		}
		return modelMap;

	}



/*
拿到  用户的 shop列表
 */

	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		/*
		没有做用户登录 暂时 设置 用户 id 为  name
		为test
		 */
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("test");

		request.getSession().setAttribute("user", user);
		user= (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);


		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());

		}
		return  modelMap;


	}
}
