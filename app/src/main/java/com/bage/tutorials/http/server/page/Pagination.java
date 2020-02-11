package com.bage.tutorials.http.server.page;

import java.io.Serializable;

public class Pagination implements Serializable {

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
    private int totalCount;

    public int getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(int targetPage) {
        this.targetPage = targetPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
