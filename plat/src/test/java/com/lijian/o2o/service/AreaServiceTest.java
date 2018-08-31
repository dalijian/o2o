package com.lijian.o2o.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.lijian.o2o.dao.BaseTest;
import com.lijian.o2o.entity.Area;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class AreaServiceTest extends BaseTest {
	@Autowired
	private AreaService areaService;
	
	@Test
	public void testQueryArea(){
		List<Area> arealist= areaService.getAreaList();
		Assert.assertEquals("西苑", arealist.get(0).getAreaName());


	}
}
