/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 接口信息Entity
 * @author dwh
 * @version 2018-07-17
 */
@Table(name="${_prefix}sys_interface_field", alias="a", columns={
		@Column(name="field_code", attrName="fieldCode", label="字段代码", isPK=true),
		@Column(name="field_name", attrName="fieldName", label="字段名称", queryType=QueryType.LIKE),
		@Column(name="interface_code", attrName="interfaceCode.interfaceCode", label="接口代码"),
		@Column(name="data_type", attrName="dataType", label="数据类型", comment="数据类型(sql数据类型)"),
		@Column(name="field_value", attrName="fieldValue", label="参数值"),
//		@Column(includeEntity=DataEntity.class),
}, orderBy="a.field_code ASC"
)
public class InterfaceField extends DataEntity<InterfaceField> {

	private static final long serialVersionUID = 1L;
	private String fieldCode;		// 字段代码
	private String fieldName;		// 字段名称
	private InterfaceInfo interfaceCode;		// 接口代码 父类
	private String dataType;		// 数据类型(sql数据类型)
	private String fieldValue;
	private List<InterfaceQuery> interfaceQueryList;


	public List<InterfaceQuery> getInterfaceQueryList() {
		return interfaceQueryList;
	}

	public void setInterfaceQueryList(List<InterfaceQuery> interfaceQueryList) {
		this.interfaceQueryList = interfaceQueryList;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public InterfaceField() {
		this(null);
	}


	public InterfaceField(InterfaceInfo interfaceCode){
		this.interfaceCode = interfaceCode;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	@NotBlank(message="字段名称不能为空")
	@Length(min=0, max=127, message="字段名称长度不能超过 127 个字符")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@NotBlank(message="接口代码不能为空")
	@Length(min=0, max=32, message="接口代码长度不能超过 32 个字符")
	public InterfaceInfo getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(InterfaceInfo interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@NotBlank(message="数据类型不能为空")
	@Length(min=0, max=32, message="数据类型长度不能超过 32 个字符")
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}