/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 订单图片表Entity
 * @author len
 * @version 2018-11-26
 */
@Table(name="${_prefix}am_order_img", alias="a", columns={
		@Column(name="img_code", attrName="imgCode", label="图片id", isPK=true),
		@Column(name="document_code", attrName="documentCode", label="订单编号"),
		@Column(name="img_url", attrName="imgUrl", label="图片地址"),
		@Column(name="img_status", attrName="imgStatus", label="图片状态"),
		@Column(name="create_date", attrName="createDate", label="创建时间"),
		@Column(name="update_date", attrName="updateDate", label="更新时间"),
	}, orderBy="a.img_code ASC"
)
public class AmOrderImg extends DataEntity<AmOrderImg> {
	
	private static final long serialVersionUID = 1L;
	private String imgCode;		// 图片id
	private String documentCode;		// 订单编号
	private String imgUrl;		// 图片地址
	private String imgStatus;
	private Date createDate;
	private Date updateDate;

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getImgStatus() {
		return imgStatus;
	}

	public void setImgStatus(String imgStatus) {
		this.imgStatus = imgStatus;
	}

	public AmOrderImg() {
		this(null);
	}

	public AmOrderImg(String id){
		super(id);
	}
	
	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	@Length(min=0, max=64, message="订单编号长度不能超过 64 个字符")
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	@Length(min=0, max=255, message="图片地址长度不能超过 255 个字符")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}