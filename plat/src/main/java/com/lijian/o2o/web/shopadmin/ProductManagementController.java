package com.lijian.o2o.web.shopadmin;


import com.liijian.o2o.enums.ProductCategoryStateEnum;
import com.lijian.o2o.dto.ProductCategoryExecution;
import com.lijian.o2o.dto.Result;
import com.lijian.o2o.entity.Product;
import com.lijian.o2o.entity.ProductCategory;
import com.lijian.o2o.entity.Shop;
import com.lijian.o2o.exception.ProductCategoryOperationException;
import com.lijian.o2o.service.ProductCategoryService;
import com.lijian.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productadmin")
public class ProductManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

//    @RequestMapping(value="getproductcategory" ,method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> getProductCategory(HttpServletRequest request){
//        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
//
//        Map<String, Object> modelMap = new HashMap<>();
//
//        if (shopId == null || shopId <= 0) {
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "shopId error");
//            return modelMap;
//        }
//        List<ProductCategory> productCategoryList =productCategoryService.getProductCategoryListByShopId(shopId);
//        if (productCategoryList != null || productCategoryList.size() > 0) {
//            modelMap.put("success", true);
//            modelMap.put("data", productCategoryList);
//
//        }else{
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "productCategory is none");
//            return modelMap;
//        }
//        throw  new RuntimeException("内部异常");
//    }

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

}