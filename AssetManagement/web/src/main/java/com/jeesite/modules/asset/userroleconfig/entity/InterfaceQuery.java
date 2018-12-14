///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.entity;
//
//import com.jeesite.common.entity.DataEntity;
//import com.jeesite.common.mybatis.annotation.Column;
//import com.jeesite.common.mybatis.annotation.Table;
//import com.jeesite.common.mybatis.mapper.query.QueryType;
//import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotBlank;
//
///**
// * 接口查询条件Entity
// * @author dwh
// * @version 2018-07-18
// */
//@Table(name="${_prefix}sys_interface_query", alias="a", columns={
//		@Column(name="query_code", attrName="queryCode", label="id", isPK=true),
//		@Column(name="query_name", attrName="queryName", label="名称", queryType=QueryType.LIKE),
//		@Column(name="field_code", attrName="fieldCode", label="字段代码"),
//		@Column(name="field_type", attrName="fieldType", label="字段值类型", comment="字段值类型(文本、登录者ID、登录者部门等)"),
//		@Column(name="field_value", attrName="fieldValue", label="字段值", comment="字段值(字段类型为文本才可编辑)"),
//		@Column(name="symbol", attrName="symbol", label="比较符", comment="比较符(=、&gt;、&lt;等)"),
//		@Column(name="interface_code", attrName="interfaceCode", label="接口code"),
//		@Column(name="user_code", attrName="userCode", label="用户code"),
//		@Column(name="sort_no", attrName="sortNo", label="排序号"),
//
////		@Column(includeEntity=DataEntity.class),
//	}, orderBy="a.sort_no ASC"
//)
//public class InterfaceQuery extends DataEntity<InterfaceQuery> {
//	private static final long serialVersionUID = 1L;
//	private String queryCode;		// id
//	private String queryName;		// 名称
//	private String fieldCode;		// 字段代码
//	private String fieldType;		// 字段值类型(文本、登录者ID、登录者部门等)
//	private String fieldValue;		// 字段值(字段类型为文本才可编辑)
//	private String symbol;		// 比较符(=、&gt;、&lt;等)
//	private String interfaceCode;
//	private String userCode;
//	private String queryListName;      //拼接起来的名字
//	private String type;       //参数类型
//	private String sortNo;
//
//	public String getSortNo() {
//		return sortNo;
//	}
//
//	public void setSortNo(String sortNo) {
//		this.sortNo = sortNo;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getQueryListName() {
//		return queryListName;
//	}
//
//	public void setQueryListName(String queryListName) {
//		this.queryListName = queryListName;
//	}
//
//	public String getInterfaceCode() {
//		return interfaceCode;
//	}
//
//	public void setInterfaceCode(String interfaceCode) {
//		this.interfaceCode = interfaceCode;
//	}
//
//	public String getUserCode() {
//		return userCode;
//	}
//
//	public void setUserCode(String userCode) {
//		this.userCode = userCode;
//	}
//
//	public InterfaceQuery() {
//		this(null);
//	}
//
//	public InterfaceQuery(String id){
//		super(id);
//	}
//
//	public String getQueryCode() {
//		return queryCode;
//	}
//
//	public void setQueryCode(String queryCode) {
//		this.queryCode = queryCode;
//	}
//
//	@NotBlank(message="名称不能为空")
//	@Length(min=0, max=127, message="名称长度不能超过 127 个字符")
//	public String getQueryName() {
//		return queryName;
//	}
//
//	public void setQueryName(String queryName) {
//		this.queryName = queryName;
//	}
//
//	@NotBlank(message="字段代码不能为空")
//	@Length(min=0, max=32, message="字段代码长度不能超过 32 个字符")
//	public String getFieldCode() {
//		return fieldCode;
//	}
//
//	public void setFieldCode(String fieldCode) {
//		this.fieldCode = fieldCode;
//	}
//
//	@NotBlank(message="字段值类型不能为空")
//	@Length(min=0, max=127, message="字段值类型长度不能超过 127 个字符")
//	public String getFieldType() {
//		return fieldType;
//	}
//
//	public void setFieldType(String fieldType) {
//		this.fieldType = fieldType;
//	}
//
//	@Length(min=0, max=500, message="字段值长度不能超过 500 个字符")
//	public String getFieldValue() {
//		return fieldValue;
//	}
//
//	public void setFieldValue(String fieldValue) {
//		this.fieldValue = fieldValue;
//	}
//
//	@NotBlank(message="比较符不能为空")
//	@Length(min=0, max=32, message="比较符长度不能超过 32 个字符")
//	public String getSymbol() {
//		return symbol;
//	}
//
//	public void setSymbol(String symbol) {
//		this.symbol = symbol;
//	}
//
//}