package com.bage.tutorials.http;


import android.util.Log;

import com.bage.tutorials.utils.JsonUtils;

import java.util.List;
import java.util.Objects;

public class HttpRequests {


    public HttpResult get(String url, List<HttpParam> params,List<HttpHeader> headers){
        Log.i("","HttpRequests get url = {}",url);
        log.info("HttpRequests get headers = {}", JsonUtils.toJson(headers));
        log.info("HttpRequests get params = {}", JsonUtils.toJson(params));

        // 构建请求参数
        HttpEntity<MultiValueMap<String, String>> requestEntity = buildHeaderAndParam(params, headers);

        // 发起请求
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        log.info("HttpRequests get statusCodeValue = {}", result.getStatusCodeValue());
        log.info("HttpRequests get body = {}", result.getBody());

        // 处理响应并返回
        return buildResult(result);
    }

    public HttpResult post(String url, List<HttpParam> params,List<HttpHeader> headers){
        log.info("HttpRequests post url = {}",url);
        log.info("HttpRequests post headers = {}", JsonUtils.toJson(headers));
        log.info("HttpRequests post params = {}", JsonUtils.toJson(params));

        // 构建请求参数
        HttpEntity<MultiValueMap<String, String>> requestEntity = buildHeaderAndParam(params, headers);

        // 发起请求
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info("HttpRequests post statusCodeValue = {}", result.getStatusCodeValue());
        log.info("HttpRequests post body = {}", result.getBody());

        // 处理响应并返回
        return buildResult(result);
    }

    private HttpResult buildResult(ResponseEntity<String> result) {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatusCode(result.getStatusCodeValue());
        httpResult.setValue(result.getBody());
        return httpResult;
    }

    private HttpEntity<MultiValueMap<String, String>> buildHeaderAndParam(List<HttpParam> params, List<HttpHeader> headers) {
        // 设置请求头
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if(Objects.nonNull(headers)){
            for (HttpHeader httpHeader : headers) {
                header.add(httpHeader.getKey(),httpHeader.getValue());
            }
        }

        // 设置参数
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        if(Objects.nonNull(params)){
            for (HttpParam param : params) {
                map.add(param.getKey(),param.getValue());
            }
        }
        // 返回
        return new HttpEntity<>(map, header);
    }

}
