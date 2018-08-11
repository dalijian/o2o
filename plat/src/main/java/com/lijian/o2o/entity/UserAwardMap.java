package com.lijian.o2o.entity;

import java.util.Date;

//顾客已领取的奖品映射
public class UserAwardMap {
	// 主键Id
	private Long userAwardId;
	// 创建时间
	private Date createTime;
	// 使用状�??0.未兑�? 1.已兑�?
	private Integer usedStatus;
	// 领取奖品�?消�?�的积分
	private Integer point;
	// 顾客信息实体�?
	private PersonInfo user;
	// 奖品信息实体�?
	private Award award;
	// 店铺信息实体�?
	private Shop shop;
	// 操作员信息实体类
	private PersonInfo operator;

	public Long getUserAwardId() {
		return userAwardId;
	}

	public void setUserAwardId(Long userAwardId) {
		this.userAwardId = userAwardId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUsedStatus() {
		return usedStatus;
	}

	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
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

	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public PersonInfo getOperator() {
		return operator;
	}

	public void setOperator(PersonInfo operator) {
		this.operator = operator;
	}

}
