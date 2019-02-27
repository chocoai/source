/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 电视在线客户端Entity
 * @author Philip Guan
 * @version 2019-02-07
 */
@Table(name="sv_tv_client", alias="a", columns={
		@Column(name="client_code", attrName="clientCode", label="编号", isPK=true),
		@Column(name="tv_code", attrName="tvCode", label="编号"),
		@Column(name="ip", attrName="ip", label="视频ID"),
		@Column(name="mac", attrName="mac", label="视频名称"),
		@Column(name="online", attrName="online", label="在线状态"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvTvClient extends DataEntity<SvTvClient> {
	
	private static final long serialVersionUID = 1L;
	private String clientCode;		// 编号
	private String tvCode;		// 编号
	private String ip;		// 电视D
	private String mac;		// 电视名称
	private String online;		// 在线状态
	
	public SvTvClient() {
		this(null);
	}

	public SvTvClient(String id){
		super(id);
	}
	
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	@NotBlank(message="编号不能为空")
	@Length(min=0, max=64, message="编号长度不能超过 64 个字符")
	public String getTvCode() {
		return tvCode;
	}

	public void setTvCode(String tvCode) {
		this.tvCode = tvCode;
	}
	
	@Length(min=0, max=255, message="电视ID长度不能超过 255 个字符")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=0, max=255, message="电视名称长度不能超过 255 个字符")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@Length(min=0, max=1, message="在线状态长度不能超过 1 个字符")
	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
	
}