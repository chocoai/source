/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 万店掌门店Entity
 * @author Philip Guan
 * @version 2019-02-19
 */
@Table(name="sv_ovopark_shop", alias="a", columns={
		@Column(name="shop_code", attrName="shopCode", label="主键", isPK=true),
		@Column(name="name", attrName="name", label="门店名称", queryType=QueryType.LIKE),
		@Column(name="device_count", attrName="deviceCount", label="设备总数"),
		@Column(name="latitude", attrName="latitude", label="纬度"),
		@Column(name="longitude", attrName="longitude", label="经度"),
		@Column(name="score", attrName="score", label="分数"),
		@Column(name="pinyin", attrName="pinyin", label="门店拼音"),
		@Column(name="py_name", attrName="pyName", label="首字母", queryType=QueryType.LIKE),
		@Column(name="location", attrName="location", label="门店区域"),
		@Column(name="total_sale", attrName="totalSale", label="销售量"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvOvoparkShop extends DataEntity<SvOvoparkShop> {
	
	private static final long serialVersionUID = 1L;
	private String shopCode;		// 主键
	private String name;		// 门店名称
	private Long deviceCount;		// 设备总数
	private Double latitude;		// 纬度
	private Double longitude;		// 经度
	private Long score;		// 分数
	private String pinyin;		// 门店拼音
	private String pyName;		// 首字母
	private Long location;		// 门店区域
	private Long totalSale;		// 销售量
	
	public SvOvoparkShop() {
		this(null);
	}

	public SvOvoparkShop(String id){
		super(id);
	}
	
	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	
	@NotBlank(message="门店名称不能为空")
	@Length(min=0, max=100, message="门店名称长度不能超过 100 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(Long deviceCount) {
		this.deviceCount = deviceCount;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
	
	@Length(min=0, max=255, message="门店拼音长度不能超过 255 个字符")
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	@Length(min=0, max=255, message="首字母长度不能超过 255 个字符")
	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}
	
	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}
	
	public Long getTotalSale() {
		return totalSale;
	}

	public void setTotalSale(Long totalSale) {
		this.totalSale = totalSale;
	}
	
}