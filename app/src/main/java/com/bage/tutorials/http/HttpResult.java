package com.bage.tutorials.http;

import com.bage.tutorials.http.server.page.RestResponse;

public class HttpResult extends RestResponse<String> {

    public boolean isOk() {
        return this.getCode() == 200;
    }
}
