package com.lijian.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="productadmin",method={RequestMethod.GET})
public class ProductAdminController {

    @RequestMapping(value = "productcategorymanagement" ,method = RequestMethod.GET)
    private  String getProductCategory(){
        return "shop/productcategory";
    }

    @RequestMapping(value = "productoperation", method = RequestMethod.GET)
    private String getproduct(){
        return "shop/productoperation";
    }

    @RequestMapping(value = "productmanagement", method = RequestMethod.GET)
    private String productManagement() {
        return "shop/productmanagement";
    }

}
