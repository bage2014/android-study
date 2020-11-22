package com.bage.tutorials.api.android;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
*  // TODO 
*
*  Created by bage on '2020-11-22 11:44:56'.
*/
@Data
public class AppFavorite implements Serializable {

    /**
    * ID主键
    */
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 被喜欢的ID主键。比如TV项的主键
    */
    private Long favoriteId;
    /**
    * 收藏类型；tv TV喜爱；city 城市
    */
    private String favoriteType;
    /**
    * 
    */
    private LocalDateTime createTime;
    /**
    * 
    */
    private LocalDateTime updateTime;
    /**
    * 
    */
    private String createStaffId;
    /**
    * 
    */
    private String updateStaffId;
    /**
    * 
    */
    private String deleteState;


}