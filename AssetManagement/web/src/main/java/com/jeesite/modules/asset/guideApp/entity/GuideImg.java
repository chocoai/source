/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 活动管理图片Entity
 * @author len
 * @version 2018-12-12
 */
@Table(name="${_prefix}guide_img", alias="a", columns={
		@Column(name="activity_code", attrName="activityCode", label="活动编码", isPK=true),
		@Column(name="banner_url", attrName="bannerUrl", label="banner_url"),
	}, orderBy="a.activity_code DESC"
)
public class GuideImg extends DataEntity<GuideImg> {
	
	private static final long serialVersionUID = 1L;
	private String activityCode;		// 活动编码
	private String bannerUrl;		// banner_url
	
	public GuideImg() {
		this(null);
	}

	public GuideImg(String id){
		super(id);
	}
	
	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	
	@Length(min=0, max=255, message="banner_url长度不能超过 255 个字符")
	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	
}