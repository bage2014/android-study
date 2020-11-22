package com.bage.tutorials.api.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult implements Serializable {

    private int code;
    private int originCode;
    private String msg;
    private String originMsg;

}
