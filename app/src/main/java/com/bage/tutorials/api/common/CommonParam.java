package com.bage.tutorials.api.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonParam implements Serializable {
    /**
     * 请求流水号
     */
    String traceId;
    /**
     * 服务ID
     */
    String serviceId;


}
