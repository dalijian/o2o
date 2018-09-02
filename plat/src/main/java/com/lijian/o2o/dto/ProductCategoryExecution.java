package com.lijian.o2o.dto;

import com.lijian.o2o.enums.ProductCategoryStateEnum;
import com.lijian.o2o.entity.ProductCategory;

import java.util.List;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    private List<ProductCategory
                > productCategoryList;

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum, List<ProductCategory> productCategoryList) {
        this.state=productCategoryStateEnum.getState();
        this.stateInfo=productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum) {

        this.state=productCategoryStateEnum.getState();
        this.stateInfo=productCategoryStateEnum.getStateInfo();
    }
    public ProductCategoryExecution(){

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
