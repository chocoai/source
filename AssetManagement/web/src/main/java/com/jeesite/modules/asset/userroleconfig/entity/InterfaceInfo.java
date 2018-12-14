///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.entity;
//
//import com.jeesite.common.collect.ListUtils;
//import com.jeesite.common.entity.DataEntity;
//import com.jeesite.common.mybatis.annotation.Column;
//import com.jeesite.common.mybatis.annotation.Table;
//import com.jeesite.common.mybatis.mapper.query.QueryType;
//import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotBlank;
//
//import java.util.Date;
//import java.util.List;
//
//
///**
// * 接口信息Entity
// * @author dwh
// * @version 2018-07-17
// */
//@Table(name="${_prefix}sys_interface", alias="a", columns={
//		@Column(name="interface_code", attrName="interfaceCode", label="id", isPK=true),
//		@Column(name="interface_name", attrName="interfaceName", label="接口名字", queryType=QueryType.LIKE),
//		@Column(name="url", attrName="url", label="路径", queryType=QueryType.LIKE),
//		@Column(includeEntity=DataEntity.class),
//		@Column(name="create_by_id", attrName="createById", label="创建人id"),
//		@Column(name="update_by_id", attrName="updateById", label="修改人ID"),
//}, orderBy="a.update_date DESC"
//)
//public class InterfaceInfo extends DataEntity<InterfaceInfo> {
//
//	private static final long serialVersionUID = 1L;
//	private String interfaceCode;		// id
//	private String interfaceName;		// 接口名字
//	private String url;		// 路径
//	private String createById;		// 创建人id
//	private String updateById;		// 修改人ID
//	private List<InterfaceField> interfaceFieldList = ListUtils.newArrayList();		// 子表列表
//	private String interfaceSql;
//	private String stitching;
//
//	public String getStitching() {
//		return stitching;
//	}
//
//	public void setStitching(String stitching) {
//		this.stitching = stitching;
//	}
//
//	private String userCode;
//
//	public String getInterfaceSql() {
//		return interfaceSql;
//	}
//
//	public void setInterfaceSql(String interfaceSql) {
//		this.interfaceSql = interfaceSql;
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
//	public InterfaceInfo() {
//		this(null);
//	}
//
//	public InterfaceInfo(String id){
//		super(id);
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
//	@NotBlank(message="接口名字不能为空")
//	@Length(min=0, max=127, message="接口名字长度不能超过 127 个字符")
//	public String getInterfaceName() {
//		return interfaceName;
//	}
//
//	public void setInterfaceName(String interfaceName) {
//		this.interfaceName = interfaceName;
//	}
//
//	@NotBlank(message="路径不能为空")
//	@Length(min=0, max=255, message="路径长度不能超过 255 个字符")
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	@Length(min=0, max=32, message="创建人id长度不能超过 32 个字符")
//	public String getCreateById() {
//		return createById;
//	}
//
//	public void setCreateById(String createById) {
//		this.createById = createById;
//	}
//
//	@Length(min=0, max=50, message="修改人ID长度不能超过 50 个字符")
//	public String getUpdateById() {
//		return updateById;
//	}
//
//	public void setUpdateById(String updateById) {
//		this.updateById = updateById;
//	}
//
//	public Date getCreateDate_gte() {
//		return sqlMap.getWhere().getValue("create_date", QueryType.GTE);
//	}
//
//	public void setCreateDate_gte(Date createDate) {
//		sqlMap.getWhere().and("create_date", QueryType.GTE, createDate);
//	}
//
//	public Date getCreateDate_lte() {
//		return sqlMap.getWhere().getValue("create_date", QueryType.LTE);
//	}
//
//	public void setCreateDate_lte(Date createDate) {
//		sqlMap.getWhere().and("create_date", QueryType.LTE, createDate);
//	}
//
//	public List<InterfaceField> getInterfaceFieldList() {
//		return interfaceFieldList;
//	}
//
//	public void setInterfaceFieldList(List<InterfaceField> interfaceFieldList) {
//		this.interfaceFieldList = interfaceFieldList;
//	}
//
//}