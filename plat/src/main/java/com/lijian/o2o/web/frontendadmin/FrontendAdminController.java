package com.lijian.o2o.web.frontendadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="frontend",method={RequestMethod.GET})
public class FrontendAdminController {

    @RequestMapping(value = "index" ,method = RequestMethod.GET)
    private  String getFrontend(){
        return "frontend/index";
    }
    @RequestMapping(value = "shoplist" ,method = RequestMethod.GET)
    private  String shoplist(){
        return "frontend/shoplist";
    }

    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String showShopDetail() {
        return "frontend/shopdetail";
    }
    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    private String showProductdetail() {
        return "frontend/productdetail";
    }

}
