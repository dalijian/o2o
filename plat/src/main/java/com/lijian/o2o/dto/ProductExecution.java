package com.lijian.o2o.dto;

import java.util.List;

import com.lijian.o2o.enums.ProductStateEnum;
import com.lijian.o2o.entity.Product;

public class ProductExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 商品数量
	private int count;

	// 操作的product（增删改商品的时候用）
	//执行增该商品时，参数 就包含 product， 返回值 封装成 ProductExecution 也包含product
	// 那么，两个product 一定不一样，执行操作后
	// 封装的ProductExecution中 product 的相关属性 已经得到更新，不然两个product 就一样了 返回的意义何在？？？
	private Product product;

	// 获取的product列表(查询商品列表的时候用)

	private List<Product> productList;

	public ProductExecution() {
	}

	// 失败的构造器
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public ProductExecution(ProductStateEnum stateEnum, Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}

	// 成功的构造器
	public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productList = productList;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
