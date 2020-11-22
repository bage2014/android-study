package com.bage.tutorials.api.android;

import com.bage.tutorials.api.common.PageQueryParam;

import lombok.Data;

@Data
public class AppFavoriteQueryParam extends PageQueryParam {

    private Long userId;

}
