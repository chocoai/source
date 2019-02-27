/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 店铺视频关系Entity
 * @author Philip Guan
 * @version 2019-01-16
 */
@Table(name="sv_video_rlat", alias="a", columns={
		@Column(name="video_rlat_code", attrName="videoRlatCode", label="关系编号", isPK=true),
		@Column(name="video_code", attrName="videoCode", label="编号"),
		@Column(name="dimension_id", attrName="dimensionId", label="类型ID"),
        @Column(name="dimension_value", attrName="dimensionValue", label="类型值"),
        @Column(name="dimension_label", attrName="dimensionLabel", label="显示值"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvVideoRlat extends DataEntity<SvVideoRlat> {
	
	private static final long serialVersionUID = 1L;
	private String videoRlatCode;		// 关系编号
	private String videoCode;		// 编号
	private String dimensionId;		// 类型ID
	private String dimensionValue;		// 类型值
	private String dimensionLabel;		// 显示值

	public SvVideoRlat() {
		this(null);
	}

	public SvVideoRlat(String id){
		super(id);
	}
	
	public String getVideoRlatCode() {
		return videoRlatCode;
	}

	public void setVideoRlatCode(String videoRlatCode) {
		this.videoRlatCode = videoRlatCode;
	}
	
	@NotBlank(message="编号不能为空")
	@Length(min=0, max=64, message="编号长度不能超过 64 个字符")
	public String getVideoCode() {
		return videoCode;
	}

	public void setVideoCode(String videoCode) {
		this.videoCode = videoCode;
	}
	
	@NotBlank(message="类型ID不能为空")
	@Length(min=0, max=255, message="类型ID长度不能超过 255 个字符")
	public String getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(String dimensionId) {
		this.dimensionId = dimensionId;
	}
	
	@NotBlank(message="类型值不能为空")
	@Length(min=0, max=255, message="类型值长度不能超过 255 个字符")
	public String getDimensionValue() {
		return dimensionValue;
	}

	public void setDimensionValue(String dimensionValue) {
		this.dimensionValue = dimensionValue;
	}

	@NotBlank(message="显示值不能为空")
	@Length(min=0, max=255, message="显示值长度不能超过 512 个字符")
	public String getDimensionLabel() {
		return dimensionLabel;
	}

	public void setDimensionLabel(String dimensionLabel) {
		this.dimensionLabel = dimensionLabel;
	}
	
}