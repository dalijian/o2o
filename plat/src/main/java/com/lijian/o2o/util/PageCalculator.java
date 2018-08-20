package com.lijian.o2o.util;

public class PageCalculator {
    //针对分页   选择 从 哪条记录 开始查找  返回 limit ？ ，pageSize ，  首个参数
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex>0)? (pageIndex-1)* pageSize:0;

    }
}
