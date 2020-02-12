package com.bage.tutorials.http.builder;

import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.http.server.page.Pagination;

import java.util.Objects;

public class HttpResultBuilder {

    private HttpResult httpResult = new HttpResult();

    /**
     * @return
     */
    public HttpResult build() {
        return httpResult;
    }

    public HttpResultBuilder success() {
        httpResult.setCode(200);
        httpResult.setMsg("success");
        return this;
    }

    public HttpResultBuilder failure() {
        httpResult.setCode(500);
        httpResult.setMsg("failure");
        return this;
    }

    /**
     * @param targetPage
     * @return
     */
    public HttpResultBuilder setTargetPage(int targetPage) {
        checkPagination();
        httpResult.getPagination().setTargetPage(targetPage);
        return this;
    }


    /**
     * 设置页面显示条目数
     *
     * @param pageSize
     * @return
     */
    public HttpResultBuilder setPageSize(int pageSize) {
        checkPagination();
        httpResult.getPagination().setPageSize(pageSize);
        return this;
    }

    /**
     * 设置总页数
     *
     * @param totalCount
     * @return
     */
    public HttpResultBuilder setTotalCount(int totalCount) {
        checkPagination();
        httpResult.getPagination().setTotalCount(totalCount);
        return this;
    }


    public int getCode() {
        return httpResult.getCode();
    }

    public HttpResultBuilder setCode(int code) {
        httpResult.setCode(code);
        return this;
    }

    public String getMsg() {
        return httpResult.getMsg();
    }

    public HttpResultBuilder setMsg(String msg) {
        httpResult.setMsg(msg);
        return this;
    }

    public String getData() {
        return httpResult.getData();
    }

    public HttpResultBuilder setData(String data) {
        httpResult.setData(data);
        return this;
    }

    public Object getBundle() {
        return httpResult.getBundle();
    }

    public HttpResultBuilder setBundle(Object bundle) {
        httpResult.setBundle(bundle);
        return this;
    }

    private void checkPagination() {
        if (Objects.isNull(httpResult.getPagination())) {
            httpResult.setPagination(new Pagination());
        }
    }
}
