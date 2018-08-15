package com.lijian.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lijian.o2o.dao.AreaDao;
import com.lijian.o2o.entity.Area;
import com.lijian.o2o.service.AreaService;
@Service(value="areaService")
@Transactional
public class AreaServiceImpl  implements AreaService{

	@Autowired
	private AreaDao areaDao;
	@Override
	public List<Area> getAreaList() {
		// TODO Auto-generated method stub
		return  this.areaDao.queryArea();
	}

}
