/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.camera.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.NotNull;

/**
 * 摄像头Entity
 * @author AlbertFeng
 * @version 2019-01-17
 */
@Table(name="sv_camera", alias="a", columns={
		@Column(name="camera_code", attrName="cameraCode", label="主键", isPK=true),
		@Column(name="device_id", attrName="deviceId", label="摄像头设备ID"),
		@Column(name="device_name", attrName="deviceName", label="摄像头设备名称", queryType=QueryType.LIKE),
		@Column(name="device_mac", attrName="deviceMac", label="摄像头设备MAC"),
		@Column(name="sort", attrName="sort", label="排序"),
		@Column(name="position", attrName="position", label="位置"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvCamera extends DataEntity<SvCamera> {
	
	private static final long serialVersionUID = 1L;
	private String cameraCode;		// 主键
	private String deviceId;		// 摄像头设备ID
	private String deviceName;		// 摄像头设备名称
	private String deviceMac;		// 摄像头设备MAC
	private Long sort;		// 排序
	private String position;		// 位置
	private List<SvTv> svTvList = ListUtils.newArrayList();		// 子表列表
	
	public SvCamera() {
		this(null);
	}

	public SvCamera(String id){
		super(id);
	}
	
	public String getCameraCode() {
		return cameraCode;
	}

	public void setCameraCode(String cameraCode) {
		this.cameraCode = cameraCode;
	}
	
	@Length(min=0, max=50, message="摄像头设备ID长度不能超过 50 个字符")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=255, message="摄像头设备名称长度不能超过 255 个字符")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	@Length(min=0, max=255, message="摄像头设备MAC长度不能超过 255 个字符")
	@NotNull(message="摄像头设备MAC不能为空")
	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=500, message="位置长度不能超过 500 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public List<SvTv> getSvTvList() {
		return svTvList;
	}

	public void setSvTvList(List<SvTv> svTvList) {
		this.svTvList = svTvList;
	}
	
}