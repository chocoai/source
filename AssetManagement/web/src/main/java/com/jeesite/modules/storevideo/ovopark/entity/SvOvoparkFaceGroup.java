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
 * 万店掌人脸分组Entity
 * @author Philip Guan
 * @version 2019-02-18
 */
@Table(name="sv_ovopark_face_group", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="orgid", attrName="orgid", label="企业Id"),
		@Column(name="group_name", attrName="groupName", label="人脸分组名称", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class SvOvoparkFaceGroup extends DataEntity<SvOvoparkFaceGroup> {
	
	private static final long serialVersionUID = 1L;
	private Long orgid;		// 企业Id
	private String groupName;		// 人脸分组名称
	
	public SvOvoparkFaceGroup() {
		this(null);
	}

	public SvOvoparkFaceGroup(String id){
		super(id);
	}
	
	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	
	@NotBlank(message="人脸分组名称不能为空")
	@Length(min=0, max=255, message="人脸分组名称长度不能超过 255 个字符")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}