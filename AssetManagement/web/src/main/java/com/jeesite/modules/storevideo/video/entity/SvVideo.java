/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 店铺视频Entity
 * @author Philip Guan
 * @version 2019-01-16
 */
@Table(name="sv_video", alias="a", columns={
		@Column(name="video_code", attrName="videoCode", label="编号", isPK=true),
		@Column(name="video_id", attrName="videoId", label="视频ID"),
		@Column(name="video_name", attrName="videoName", label="视频名称", queryType=QueryType.LIKE),
		@Column(name="video_type", attrName="videoType", label="视频类型"),
		@Column(name="data_sort", attrName="dataSort", label="排序"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvVideo extends DataEntity<SvVideo> {
	
	private static final long serialVersionUID = 1L;
	private String videoCode;		// 编号
	private String videoId;		// 视频ID
	private String videoName;		// 视频名称
	private String videoType;		// 视频类型
    private Integer dataSort;		// 排序
	
	public SvVideo() {
		this(null);
	}

	public SvVideo(String id){
		super(id);
	}
	
	public String getVideoCode() {
		return videoCode;
	}

	public void setVideoCode(String videoCode) {
		this.videoCode = videoCode;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	@Length(min=0, max=255, message="视频名称长度不能超过 255 个字符")
	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    @NotNull(message="排序不能为空")
	public Integer getDataSort() {
		return dataSort;
	}

	public void setDataSort(Integer dataSort) {
		this.dataSort = dataSort;
	}
	
}