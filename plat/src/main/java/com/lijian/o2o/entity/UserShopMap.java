package com.lijian.o2o.entity;

import java.util.Date;

//顾客店铺积分映射
public class UserShopMap {
	// 主键Id
	private Long userShopId;
	// 创建时间
	private Date createTime;
	// 顾客在该店铺的积�?
	private Integer point;
	// 顾客信息实体�?
	private PersonInfo user;
	// 店铺信息实体�?
	private Shop shop;

	public Long getUserShopId() {
		return userShopId;
	}

	public void setUserShopId(Long userShopId) {
		this.userShopId = userShopId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public PersonInfo getUser() {
		return user;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
