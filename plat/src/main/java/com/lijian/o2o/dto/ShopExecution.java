package com.lijian.o2o.dto;

import java.util.List;

import com.liijian.o2o.enums.ShopStateEnum;
import com.lijian.o2o.entity.Shop;

public class ShopExecution {
	/*
	 * 状态值
	 */
	private int state;
	/*
	 * 状态标识
	 */
	private String stateInfo;
	/*
	 * 店铺数量
	 */
	private int count;
	/*
	 * 操作shop
	 */
	private Shop shop;
	/*
	 * 查询店铺列表
	 */
	private List<Shop> shopList;
	

	public ShopExecution(){
		
	}
	/*
	 * 店铺操作失败时使用的构造器
	 */
	public ShopExecution(ShopStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		
	}
	/*
	 * 店铺操作成功时使用的构造器
	 */
	public ShopExecution(ShopStateEnum stateEnum,Shop shop){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.shop=shop;
	}
	/*
	 * 返回列表使用的构造器
	 */
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.shopList=shopList;
		
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
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	
	
}
