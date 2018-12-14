/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermenu.entity;

import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.sys.entity.DictData;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 绩效卡用户菜单Entity
 * @author Philip Guan
 * @version 2018-11-22
 */
@Table(name="ach_user_menu", alias="a", columns={
		@Column(name="user_menu_code", attrName="userMenuCode", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="员工编码"),
		@Column(name="dict_code", attrName="dictCode", label="字典编码"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),},
		joinTable = {
			@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = DictData.class, alias = "b",
					on = "a.dict_code = b.dict_code", attrName = "dictData",
					columns = {@Column(name = "dict_code", label = "字典编码", isPK = true),
							@Column(name = "dict_value", label = "字典键值", isQuery = false),
							@Column(name = "dict_label", label = "字典标签", isQuery = false),
							@Column(name = "description", label = "字典描述", isQuery = false),
							@Column(name = "remarks", label = "备注", isQuery = false),
					}),
	}, orderBy="a.update_date DESC"
)
public class AchUserMenu extends DataEntity<AchUserMenu> {

	private static final long serialVersionUID = 1L;
	private String userMenuCode;		// 主键
	private String userId;		// 员工编码
	private String dictCode;		// 字典编码
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间

	private DictData dictData;
	
	public AchUserMenu() {
		this(null);
	}

	public AchUserMenu(String id){
		super(id);
	}
	
	public String getUserMenuCode() {
		return userMenuCode;
	}

	public void setUserMenuCode(String userMenuCode) {
		this.userMenuCode = userMenuCode;
	}
	
	@Length(min=0, max=64, message="员工编码长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotBlank(message="字典编码不能为空")
	@Length(min=0, max=64, message="字典编码长度不能超过 64 个字符")
	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	
	@Length(min=0, max=64, message="审核人长度不能超过 64 个字符")
	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public DictData getDictData() {
		return dictData;
	}

	public void setDictData(DictData dictData) {
		this.dictData = dictData;
	}
	
}