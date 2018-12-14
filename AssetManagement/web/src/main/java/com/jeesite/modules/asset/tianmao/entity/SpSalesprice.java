/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 商品销售价目Entity
 * @author dwh
 * @version 2018-08-16
 */
@Table(name="${_prefix}sp_salesprice", alias="a", columns={
		@Column(name="table_code", attrName="tableCode", label="table_code", isPK=true),
		@Column(name="f_id", attrName="fId", label="fid_uuid"),
		@Column(name="f_material_code", attrName="fMaterialCode", label="物料编码"),
		@Column(name="f_lastmodify_date", attrName="fLastmodifyDate", label="最后修改时间"),
		@Column(name="f_price", attrName="fPrice", label="价格"),
		@Column(name="f_delivery_cycle", attrName="fDeliveryCycle", label="发货周期"),
		@Column(name="f_isallow_sync", attrName="fIsallowSync", label="是否允许同步"),
		@Column(name="f_create_date", attrName="fCreateDate", label="创建日期"),
		@Column(name="f_create_userid", attrName="fCreateUserid", label="创建用户主键"),
		@Column(name="f_create_username", attrName="fCreateUsername", label="创建用户"),
		@Column(name="f_modify_date", attrName="fModifyDate", label="修改日期"),
		@Column(name="f_modify_userid", attrName="fModifyUserid", label="修改用户主键"),
		@Column(name="f_modify_username", attrName="fModifyUsername", label="修改用户"),
	}, orderBy="a.table_code DESC"
)
public class SpSalesprice extends DataEntity<SpSalesprice> {
	
	private static final long serialVersionUID = 1L;
	private String tableCode;		// table_code
	private String fId;		// fid_uuid
	private String fMaterialCode;		// 物料编码
	private Date fLastmodifyDate;		// 最后修改时间
	private Float fPrice;		// 价格
	private Long fDeliveryCycle;		// 发货周期
	private String fIsallowSync;		// 是否允许同步
	private Date fCreateDate;		// 创建日期
	private String fCreateUserid;		// 创建用户主键
	private String fCreateUsername;		// 创建用户
	private Date fModifyDate;		// 修改日期
	private String fModifyUserid;		// 修改用户主键
	private String fModifyUsername;		// 修改用户
	
	public SpSalesprice() {
		this(null);
	}

	public SpSalesprice(String id){
		super(id);
	}
	
	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	
	@Length(min=0, max=255, message="fid_uuid长度不能超过 255 个字符")
	public String getFId() {
		return fId;
	}

	public void setFId(String fId) {
		this.fId = fId;
	}
	
	@Length(min=0, max=255, message="物料编码长度不能超过 255 个字符")
	public String getFMaterialCode() {
		return fMaterialCode;
	}

	public void setFMaterialCode(String fMaterialCode) {
		this.fMaterialCode = fMaterialCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFLastmodifyDate() {
		return fLastmodifyDate;
	}

	public void setFLastmodifyDate(Date fLastmodifyDate) {
		this.fLastmodifyDate = fLastmodifyDate;
	}

	public Float getFPrice() {
		return fPrice;
	}

	public void setFPrice(Float fPrice) {
		this.fPrice = fPrice;
	}

	public Long getFDeliveryCycle() {
		return fDeliveryCycle;
	}

	public void setFDeliveryCycle(Long fDeliveryCycle) {
		this.fDeliveryCycle = fDeliveryCycle;
	}
	
	@Length(min=0, max=2, message="是否允许同步长度不能超过 2 个字符")
	public String getFIsallowSync() {
		return fIsallowSync;
	}

	public void setFIsallowSync(String fIsallowSync) {
		this.fIsallowSync = fIsallowSync;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFCreateDate() {
		return fCreateDate;
	}

	public void setFCreateDate(Date fCreateDate) {
		this.fCreateDate = fCreateDate;
	}
	
	@Length(min=0, max=255, message="创建用户主键长度不能超过 255 个字符")
	public String getFCreateUserid() {
		return fCreateUserid;
	}

	public void setFCreateUserid(String fCreateUserid) {
		this.fCreateUserid = fCreateUserid;
	}
	
	@Length(min=0, max=255, message="创建用户长度不能超过 255 个字符")
	public String getFCreateUsername() {
		return fCreateUsername;
	}

	public void setFCreateUsername(String fCreateUsername) {
		this.fCreateUsername = fCreateUsername;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFModifyDate() {
		return fModifyDate;
	}

	public void setFModifyDate(Date fModifyDate) {
		this.fModifyDate = fModifyDate;
	}
	
	@Length(min=0, max=255, message="修改用户主键长度不能超过 255 个字符")
	public String getFModifyUserid() {
		return fModifyUserid;
	}

	public void setFModifyUserid(String fModifyUserid) {
		this.fModifyUserid = fModifyUserid;
	}
	
	@Length(min=0, max=255, message="修改用户长度不能超过 255 个字符")
	public String getFModifyUsername() {
		return fModifyUsername;
	}

	public void setFModifyUsername(String fModifyUsername) {
		this.fModifyUsername = fModifyUsername;
	}
	
}