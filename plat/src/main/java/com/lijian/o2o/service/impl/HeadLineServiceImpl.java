package com.lijian.o2o.service.impl;

import com.lijian.o2o.dao.HeadLineDao;
import com.lijian.o2o.entity.HeadLine;
import com.lijian.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Override
    public List<HeadLine> getHeadLine(HeadLine headLineCondition) {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
