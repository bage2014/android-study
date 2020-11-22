package com.bage.tutorials.api.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class PagePagination implements Serializable {

    /**
     * 目标页面
     */
    private int targetPage;
    /**
     * 每页最多显示条目
     */
    private int pageSize;
    /**
     * 总条目
     */
    private int total;

}
