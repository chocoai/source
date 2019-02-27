/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 万店掌设备Entity
 * @author Philip Guan
 * @version 2019-02-18
 */
@Table(name="sv_ovopark_device", alias="a", columns={
		@Column(name="device_id", attrName="deviceId", label="主键"),
		@Column(name="device_code", attrName="deviceCode", label="主键", isPK=true),
		@Column(name="device_mac", attrName="deviceMac", label="设备mac"),
		@Column(name="device_name", attrName="deviceName", label="设备名称", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
		@Column(name="group_id", attrName="groupId", label="分组ID"),
		@Column(name="org_id", attrName="orgId", label="企业id"),
}, orderBy="a.update_date DESC"
)
public class SvOvoparkDevice extends DataEntity<SvOvoparkDevice> {

	private static final long serialVersionUID = 1L;
	private Long deviceId;		// 主键
	private String deviceCode;		// 主键
	private String deviceMac;		// 设备mac
	private String deviceName;		// 设备名称
	private Long groupId;		// 分组ID
	private Long orgId;		// 企业id

	public SvOvoparkDevice() {
		this(null);
	}

	public SvOvoparkDevice(String id){
		super(id);
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	@Length(min=0, max=255, message="设备名称长度不能超过 255 个字符")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}