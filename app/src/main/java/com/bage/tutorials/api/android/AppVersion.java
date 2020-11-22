package com.bage.tutorials.api.android;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * // TODO
 * <p>
 * Created by bage on '2020-11-11 14:11:11'.
 */
@Data
public class AppVersion implements Serializable {

    /**
     * ID主键
     */
    private Long id;
    /**
     * 版本号
     */
    private Integer versionCode;
    /**
     * 描述说明
     */
    private String description;
    /**
     * apk文件ID
     */
    private Long fileId;
    /**
     * 文件URL链接
     */
    private String fileUrl;
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 文件大小(kb)
     */
    private Long fileSize;
    /**
     * 更新类型：force_update强制更新；其他默认
     */
    private UpdateTypeEnum updateType;
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