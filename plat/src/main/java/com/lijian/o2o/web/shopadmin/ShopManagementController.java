package com.lijian.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA_2_3.portable.InputStream;
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
import com.lijian.o2o.entity.PersonInfo;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.service.ShopService;
import com.lijian.o2o.util.HttpServletRequestUtil;
import com.lijian.o2o.util.ImageUtil;
import com.lijian.o2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")

public class ShopManagementController {
	@Autowired
	private ShopService shopService;

	/**
	 * 注册店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		//返回注册信息结果 给前端
		Map<String, Object> modelMap = new HashMap<String, Object>();

		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			//通过Gson 将前端传递的json 转换成shop 实例
			shop = new Gson().fromJson(shopStr, Shop.class);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		
		
		//将上传文件的文件流 转换成 CommonsMultipartFile
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
			File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
			try {
				/*
				 * createNewFile()当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。
				 * 检查文件是否存在，若不存在则创建该文件
				 */
				shopImgFile.createNewFile();
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			
			
			
			//添加店铺
			ShopExecution se = shopService.addShop(shop, shopImgFile);
			
			//检查返回值
			if(se.getState()==ShopStateEnum.CHECK.getState()){
				modelMap.put("success", true);
				
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;

		}
		return null;
	}

	/**
	 * 将输入流转换成 File 方便 service层 测试
	 * 
	 * @param ins
	 * @param file
	 */

	private static void inputStreamToFile(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用inputStreamtoFile 产生异常:" + e.getMessage());

		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("关闭inputStream 产生异常:" + e.getMessage());
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("关闭outputStream产生异常:" + e.getMessage());
				}
			}
		}
	}
}
