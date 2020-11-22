package com.bage.tutorials.api.common;

import lombok.Data;

@Data
public class PageQueryParam extends CommonParam {

    private int targetPage= 1;
    private int pageSize = 10;

}
