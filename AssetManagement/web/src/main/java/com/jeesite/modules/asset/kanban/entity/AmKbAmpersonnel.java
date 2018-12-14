/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 看板有效人员维护Entity
 * @author dwh
 * @version 2018-07-24
 */
@Table(name="${_prefix}am_kb_ampersonnel", alias="a", columns={
		@Column(name="kb_ampersonnel_code", attrName="kbAmpersonnelCode", label="有效人员编码", isPK=true),
		@Column(name="phone", attrName="phone", label="联系电话",queryType=QueryType.LIKE),
		@Column(name="ampersonnel", attrName="ampersonnel", label="有效人员",queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
		@Column(name="kanban_code", attrName="kanbanCode", label="kanban_code"),
	},  joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmKanbanFile.class, alias="b",
				on="b.kanban_code=a.kanban_code ",
				columns={@Column(includeEntity=AmKanbanFile.class)})
}, orderBy="a.update_date DESC"
)
public class AmKbAmpersonnel extends DataEntity<AmKbAmpersonnel> {
	
	private static final long serialVersionUID = 1L;
	private String kbAmpersonnelCode;		// 有效人员编码
	private String phone;		// 联系电话
	private String ampersonnel;		// 有效人员
	private String kanbanCode;		// kanban_code
	private AmKanbanFile amKanbanFile;
	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public AmKanbanFile getAmKanbanFile() {
		return amKanbanFile;
	}

	public void setAmKanbanFile(AmKanbanFile amKanbanFile) {
		this.amKanbanFile = amKanbanFile;
	}

	public AmKbAmpersonnel() {
		this(null,null);
	}

	public AmKbAmpersonnel(String kbAmpersonnelCode, String phone){
		this.kbAmpersonnelCode = kbAmpersonnelCode;
		this.phone = phone;
	}
	
	public String getKbAmpersonnelCode() {
		return kbAmpersonnelCode;
	}

	public void setKbAmpersonnelCode(String kbAmpersonnelCode) {
		this.kbAmpersonnelCode = kbAmpersonnelCode;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotBlank(message="有效人员不能为空")
	@Length(min=0, max=100, message="有效人员长度不能超过 100 个字符")
	public String getAmpersonnel() {
		return ampersonnel;
	}

	public void setAmpersonnel(String ampersonnel) {
		this.ampersonnel = ampersonnel;
	}
	
	@NotBlank(message="kanban_code不能为空")
	@Length(min=0, max=64, message="kanban_code长度不能超过 64 个字符")
	public String getKanbanCode() {
		return kanbanCode;
	}

	public void setKanbanCode(String kanbanCode) {
		this.kanbanCode = kanbanCode;
	}
	
}