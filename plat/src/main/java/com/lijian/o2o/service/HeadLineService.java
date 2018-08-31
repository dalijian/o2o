package com.lijian.o2o.service;

import com.lijian.o2o.entity.HeadLine;

import java.util.List;

public interface HeadLineService {
    List<HeadLine> getHeadLine(HeadLine headLineCondition);
}
