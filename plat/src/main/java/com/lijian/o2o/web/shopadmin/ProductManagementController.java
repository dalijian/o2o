package com.lijian.o2o.web.shopadmin;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.liijian.o2o.enums.ProductCategoryStateEnum;
import com.liijian.o2o.enums.ProductStateEnum;
import com.lijian.o2o.dto.ProductCategoryExecution;
import com.lijian.o2o.dto.ProductExecution;
import com.lijian.o2o.dto.Result;
import com.lijian.o2o.entity.Product;
import com.lijian.o2o.entity.ProductCategory;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.exception.ProductCategoryOperationException;
import com.lijian.o2o.exception.ProductOperationException;
import com.lijian.o2o.service.ProductCategoryService;
import com.lijian.o2o.service.ProductService;
import com.lijian.o2o.util.CodeUtil;
import com.lijian.o2o.util.HttpServletRequestUtil;
import com.lijian.o2o.util.ImageHolder;

@Controller
@RequestMapping("/productadmin")
public class ProductManagementController {
	
	private Logger logger = LoggerFactory.getLogger(ProductManagementController.class);
	
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;


    private final static Integer MAX_UPLOAD_IMG_COUNT = 6;

    /**
     * 商品类别 管理
     * @param request
     * @return
     */


    @RequestMapping(value = "productcategorydelete", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> productcategorydelete(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long productCategoryId = HttpServletRequestUtil.getLong(request, "productcategoryId");
        if (productCategoryId == null || productCategoryId <= 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "productCategoryId error");
            return modelMap;
        }
        int resultColumn = productCategoryService.delProductCategory(productCategoryId);
        if (resultColumn == 1) {
            modelMap.put("success", true);


        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "delete fail");
            return modelMap;
        }
        throw new RuntimeException("内部异常");

    }

    @RequestMapping(value = "getproductcategory", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryListByShopId(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);

        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
        }

    }

    @RequestMapping(value = "addproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addBatchProductCategory(HttpServletRequest request,
                                                        @RequestBody List<ProductCategory> productCategoryList) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());

        }


        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());

                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;

            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");

        }
        return modelMap;


    }

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null || productCategoryId > 0) {
                    try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                
                System.out.println(productCategoryId);
                System.out.println(currentShop.getShopId());
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(currentShop.getShopId(), productCategoryId);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "productCategoryId error");

        }
        return modelMap;
    }

    //    增加商品
    @RequestMapping(value = "addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) throws IOException {
        Map<String, Object> modelMap = new HashMap<String, Object>();
     // 从当前session中获取店铺信息，主要是获取shopId
     		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
     		
     		
     		
     		logger.info(currentShop.toString());
        //校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        Product product ;
        try {
             product = new Gson().fromJson(productStr, Product.class);
        } catch (ProductOperationException e) {

            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        product.setShop(currentShop);;

        // 将上传文件的文件流 转换成 CommonsMultipartFile

        CommonsMultipartFile productImg = null;
//        上传缩略图


        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

       
        
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //商品缩略图
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");

            productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
            ImageHolder imageHolderProduct = new ImageHolder(productImg.getOriginalFilename(),productImg.getInputStream());
            //商品详细图


            List<ImageHolder> imageHolderList = new ArrayList<>();

            for (int i = 0; i < MAX_UPLOAD_IMG_COUNT; i++) {

                CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                if (productImgFile!=null){

                    ImageHolder productImgS = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                    imageHolderList.add(productImgS);
                }else {
                    break;
                }
            }
            //调用 addProduct 添加 商品
            try {

                ProductExecution productExecution = productService.addProduct(product, imageHolderProduct, imageHolderList);
                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                }


            } catch (ProductOperationException e) {
                throw new ProductOperationException("商品添加错误");
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }



    return  modelMap;
    }
    //    增加商品
    @RequestMapping(value = "modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) throws IOException {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        if (HttpServletRequestUtil.getString(request, "statusChange") == null || !HttpServletRequestUtil.getString(request, "statusChange").equals("true")) {

            //校验验证码
            if (!CodeUtil.checkVerifyCode(request)) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "输入了错误的验证码");
                return modelMap;
            }
        }

            // 将上传文件的文件流 转换成 CommonsMultipartFile

            CommonsMultipartFile productImg = null;
//        上传缩略图

            
            ImageHolder productDetailImg = null;

            List<ImageHolder> productImgList = null;

            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());

            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //商品缩略图


                productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
                productDetailImg = new ImageHolder(productImg.getOriginalFilename(), productImg.getInputStream());
                //商品详细图


                productImgList = new ArrayList<>();

                for (int i = 0; i < MAX_UPLOAD_IMG_COUNT; i++) {

                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (productImgFile != null) {

                        ImageHolder productImgS = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImgS);
                    } else {
                        break;
                    }
                }

            } 
            
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            Product product;
            try {
                product = new Gson().fromJson(productStr, Product.class);
            } catch (ProductOperationException e) {

                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
            if (product != null) {
                try {
                    Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                    product.setShop(currentShop);

                    ProductExecution pe = productService.modifyProduct(product, productDetailImg, productImgList);
                    if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
//                    当调用service 层方法时 没拿到正确值  也没有 使用 try catch 捕捉 错误 调用 service 方法返回值中的错误信息
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", pe.getStateInfo());
                    }
                    //当发生异常是才执行catch 语句
                } catch (ProductOperationException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.toString());
                    return modelMap;
                }

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "请输入商品信息");
            }


            return modelMap;
        }
    








    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
           Product product= productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryListByShopId(product.getShop().getShopId());
            modelMap.put("success", true);
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);


        }
        else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");

        }
        return modelMap;
    }
	private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 取出缩略图并构建ImageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
		if (thumbnailFile != null) {
			thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
		}
		// 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
		for (int i = 0; i < MAX_UPLOAD_IMG_COUNT; i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
			if (productImgFile != null) {
				// 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
				ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
						productImgFile.getInputStream());
				productImgList.add(productImg);
			} else {
				// 若取出的第i个详情图片文件流为空，则终止循环
				break;
			}
		}
		return thumbnail;
	}

    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {

            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
        }


    /**
     * 封装商品查询条件到Product实例中
     *
     * @param shopId(mandatory)
     * @param productCategoryId(optional)
     * @param productName(optional)
     * @return
     */
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        // 若有指定类别的要求则添加进去
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        // 若有商品名模糊查询的要求则添加进去
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}