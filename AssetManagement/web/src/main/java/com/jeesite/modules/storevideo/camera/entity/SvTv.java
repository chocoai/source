/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.camera.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 摄像头Entity
 * @author AlbertFeng
 * @version 2019-01-17
 */
@Table(name="sv_tv", alias="a", columns={
		@Column(name="tv_code", attrName="tvCode", label="主键", isPK=true),
		@Column(name="camera_code", attrName="cameraCode.cameraCode", label="摄像头主键"),
		@Column(name="tv_number", attrName="tvNumber", label="编号"),
		@Column(name="tv_name", attrName="tvName", label="名称", queryType=QueryType.LIKE),
		@Column(name="position", attrName="position", label="位置"),
		@Column(name="video_id", attrName="videoId", label="视频Id"),
		@Column(name="video_name", attrName="videoName", label="视频名称", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.create_date ASC"
)
public class SvTv extends DataEntity<SvTv> {
	
	private static final long serialVersionUID = 1L;
	private String tvCode;		// 主键
	private SvCamera cameraCode;		// 摄像头主键 父类
	private String tvNumber;		// 编号
	private String tvName;		// 名称
	private String position;		// 位置
	private String videoId;		// 视频Id
	private String videoName;		// 视频名称
	
	public SvTv() {
		this(null);
	}


	public SvTv(SvCamera cameraCode){
		this.cameraCode = cameraCode;
	}
	
	public String getTvCode() {
		return tvCode;
	}

	public void setTvCode(String tvCode) {
		this.tvCode = tvCode;
	}
	
	@Length(min=0, max=64, message="摄像头主键长度不能超过 64 个字符")
	public SvCamera getCameraCode() {
		return cameraCode;
	}

	public void setCameraCode(SvCamera cameraCode) {
		this.cameraCode = cameraCode;
	}
	
	@Length(min=0, max=255, message="编号长度不能超过 255 个字符")
	public String getTvNumber() {
		return tvNumber;
	}

	public void setTvNumber(String tvNumber) {
		this.tvNumber = tvNumber;
	}
	
	@Length(min=0, max=255, message="名称长度不能超过 255 个字符")
	public String getTvName() {
		return tvName;
	}

	public void setTvName(String tvName) {
		this.tvName = tvName;
	}
	
	@Length(min=0, max=255, message="位置长度不能超过 255 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=255, message="视频Id长度不能超过 255 个字符")
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
	
}