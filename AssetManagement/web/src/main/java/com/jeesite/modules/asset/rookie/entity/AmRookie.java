/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.rookie.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 菜鸟对接记录Entity
 * @author czy
 * @version 2018-07-03
 */
@Table(name="${_prefix}am_rookie", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据编号", isPK=true),
		@Column(name="document_name", attrName="documentName", label="单据名称", queryType=QueryType.LIKE),
		@Column(name="operation_type", attrName="operationType", label="操作类型"),
		@Column(name="busine_date", attrName="busineDate", label="业务时间"),
		@Column(name="operation_by", attrName="operationBy", label="操作人员"),
		@Column(name="order_code", attrName="orderCode", label="源单编号"),
		@Column(name="resp_code", attrName="respCode", label="响应编号"),
		@Column(name="resp_info", attrName="respInfo", label="响应信息"),
		@Column(name="success", attrName="success", label="是否成功"),
		@Column(name="wms_code", attrName="wmsCode", label="WMS单据编号"),
	}, orderBy="a.document_code DESC"
)
public class AmRookie extends DataEntity<AmRookie> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据编号
	private String documentName;		// 单据名称
	private String operationType;		// 操作类型
	private Date busineDate;		// 业务时间
	private String operationBy;		// 操作人员
	private String orderCode;		// 源单编号
	private String respCode;		// 响应编号
	private String respInfo;		// 响应信息
	private String success;		// 是否成功
	private String wmsCode;		// WMS单据编号
	private List<AmRookieDetail> amRookieDetailList = ListUtils.newArrayList();		// 子表列表
	
	public AmRookie() {
		this(null);
	}

	public AmRookie(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=50, message="单据名称长度不能超过 50 个字符")
	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	@Length(min=0, max=20, message="操作类型长度不能超过 20 个字符")
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBusineDate() {
		return busineDate;
	}

	public void setBusineDate(Date busineDate) {
		this.busineDate = busineDate;
	}
	
	@Length(min=0, max=50, message="操作人员长度不能超过 50 个字符")
	public String getOperationBy() {
		return operationBy;
	}

	public void setOperationBy(String operationBy) {
		this.operationBy = operationBy;
	}
	
	@Length(min=0, max=50, message="源单编号长度不能超过 50 个字符")
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@Length(min=0, max=50, message="响应编号长度不能超过 50 个字符")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	@Length(min=0, max=1000, message="响应信息长度不能超过 1000 个字符")
	public String getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}
	
	@Length(min=0, max=10, message="是否成功长度不能超过 10 个字符")
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	@Length(min=0, max=64, message="WMS单据编号长度不能超过 64 个字符")
	public String getWmsCode() {
		return wmsCode;
	}

	public void setWmsCode(String wmsCode) {
		this.wmsCode = wmsCode;
	}
	
	public List<AmRookieDetail> getAmRookieDetailList() {
		return amRookieDetailList;
	}

	public void setAmRookieDetailList(List<AmRookieDetail> amRookieDetailList) {
		this.amRookieDetailList = amRookieDetailList;
	}
	
}