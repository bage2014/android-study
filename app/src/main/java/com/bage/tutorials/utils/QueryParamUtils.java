package com.bage.tutorials.utils;

import com.bage.tutorials.api.common.PageQueryParam;

public class QueryParamUtils {

    /**
     *
     * @param param
     * @return
     */
    public static void setCommonParam(PageQueryParam param) {
        param.setTargetPage(1);
        param.setPageSize(10);
    }
}
