package com.lijian.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lijian.o2o.entity.Area;

import junit.framework.Assert;

public class AreaDaoTest  extends BaseTest{
	@Autowired
	private AreaDao areaDao;//没有实现areaDao继承类 为什么还能注入成功？？？
	
	@Test
	public void testQueryArea(){
		List<Area> arealist= areaDao.queryArea();
		Assert.assertEquals(2, arealist.size());
	}

}
