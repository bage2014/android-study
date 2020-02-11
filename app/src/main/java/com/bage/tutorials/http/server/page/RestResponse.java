package com.bage.tutorials.http.server.page;


public class RestResponse<T> extends com.bage.tutorials.http.server.RestResponse <T> {

    /**
     * 分页信息
     */
    Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
