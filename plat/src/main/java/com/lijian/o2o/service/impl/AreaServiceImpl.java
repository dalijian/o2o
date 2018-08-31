package com.lijian.o2o.service.impl;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lijian.o2o.cache.JedisUtil;
import com.lijian.o2o.exception.AreaOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lijian.o2o.dao.AreaDao;
import com.lijian.o2o.entity.Area;
import com.lijian.o2o.service.AreaService;

@Service(value = "areaService")
@Transactional
public class AreaServiceImpl implements AreaService {


    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;


    private Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    @Transactional
    public List<Area> getAreaList() {
        // 定义redis的key
        String key = AREALISTKEY;
        // 定义接收对象
        List<Area> areaList = null;
        // 定义jackson数据转换操作类

        // 判断key是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库里面取出相应数据
            areaList = areaDao.queryArea();
            // 将相关的实体类集合转换成string,存入redis里面对应的key中
            String jsonString;
            try {
                logger.info(areaList.toString());
                jsonString = new Gson().toJson(areaList);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            // 若存在，则直接从redis里面取出相应数据
            String jsonString = jedisStrings.get(key);
            // 指定要将string转换成的集合类型
            try {
                // 将相关key对应的value里的的string转换成对象的实体类集合
                areaList = new Gson().fromJson(jsonString, new TypeToken<List<Area>>() {
                }.getType());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage()
                );
            }


        }
        return areaList;

    }
}
