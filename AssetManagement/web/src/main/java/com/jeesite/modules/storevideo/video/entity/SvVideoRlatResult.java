/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 店铺视频关系Entity
 * @author Philip Guan
 * @version 2019-01-16
 */
public class SvVideoRlatResult {

	private String videoCode;		// 编号
    private String videoScore;		// 类型ID

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getVideoScore() {
        return videoScore;
    }

    public void setVideoScore(String videoScore) {
        this.videoScore = videoScore;
    }

}