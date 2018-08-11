package com.lijian.o2o.entity;

import java.util.Date;

/**
 * 微信登录实体�?
 * 
 * @author xiangze
 *
 */
public class WechatAuth {
	// 主键ID
	private Long wechatAuthId;
	// 微信获取用户信息的凭证，对于某个公众号具有唯�?�?
	private String openId;
	// 创建时间
	private Date createTime;
	// 用户信息
	private PersonInfo personInfo;

	public Long getWechatAuthId() {
		return wechatAuthId;
	}

	public void setWechatAuthId(Long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

}
