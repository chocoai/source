/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 屏幕配置Entity
 * @author len
 * @version 2018-12-21
 */
@Table(name="${_prefix}screen_config", alias="a", columns={
		@Column(name="configure_code", attrName="configureCode", label="配置项编码", isPK=true),
		@Column(name="configure_status", attrName="configureStatus", label="状态"),
		@Column(name="configure_shop", attrName="configureShop", label="配置店铺"),
		@Column(name="configure_page", attrName="configurePage", label="配置页面"),
		@Column(name="page_location", attrName="pageLocation", label="页面位置"),
	}, orderBy="a.configure_code DESC"
)
public class ScreenConfig extends DataEntity<ScreenConfig> {
	
	private static final long serialVersionUID = 1L;
	private String configureCode;		// 配置项编码
	private String configureStatus;		// 状态
	private String configureShop;		// 配置店铺
	private String configurePage;		// 配置页面
	private String pageLocation;		// 页面位置

	private List<ScreenConfigDetail> screenConfigDetailList = ListUtils.newArrayList();		// 子表列表
	private List<ScreenEnterpriseDetail> screenEnterpriseDetailList = ListUtils.newArrayList();		// 子表列表
	
	public ScreenConfig() {
		this(null);
	}

	public ScreenConfig(String id){
		super(id);
	}
	
	public String getConfigureCode() {
		return configureCode;
	}

	public void setConfigureCode(String configureCode) {
		this.configureCode = configureCode;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getConfigureStatus() {
		return configureStatus;
	}

	public void setConfigureStatus(String configureStatus) {
		this.configureStatus = configureStatus;
	}
	
	@Length(min=0, max=1, message="配置店铺长度不能超过 1 个字符")
	public String getConfigureShop() {
		return configureShop;
	}

	public void setConfigureShop(String configureShop) {
		this.configureShop = configureShop;
	}
	
	@Length(min=0, max=1, message="配置页面长度不能超过 1 个字符")
	public String getConfigurePage() {
		return configurePage;
	}

	public void setConfigurePage(String configurePage) {
		this.configurePage = configurePage;
	}
	
	public List<ScreenConfigDetail> getScreenConfigDetailList() {
		return screenConfigDetailList;
	}

	public void setScreenConfigDetailList(List<ScreenConfigDetail> screenConfigDetailList) {
		this.screenConfigDetailList = screenConfigDetailList;
	}

	public String getPageLocation() {
		return pageLocation;
	}

	public void setPageLocation(String pageLocation) {
		this.pageLocation = pageLocation;
	}

	public List<ScreenEnterpriseDetail> getScreenEnterpriseDetailList() {
		return screenEnterpriseDetailList;
	}

	public void setScreenEnterpriseDetailList(List<ScreenEnterpriseDetail> screenEnterpriseDetailList) {
		this.screenEnterpriseDetailList = screenEnterpriseDetailList;
	}

}