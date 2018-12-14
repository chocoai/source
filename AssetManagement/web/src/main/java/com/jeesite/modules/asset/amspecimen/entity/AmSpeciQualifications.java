/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 样品进度单文件表Entity
 * @author dwh
 * @version 2018-07-10
 */
@Table(name="${_prefix}am_speci_qualifications", alias="a", columns={
		@Column(name="speci_qualifications_code", attrName="speciQualificationsCode", label="文件编号", isPK=true),
		@Column(name="code", attrName="code", label="样品进度code"),
		@Column(name="qualification_name", attrName="qualificationName", label="文件名", queryType=QueryType.LIKE),
		@Column(name="type_name", attrName="typeName", label="文件类型", queryType=QueryType.LIKE),
		@Column(name="profile_surfix", attrName="profileSurfix", label="profile_surfix"),
		@Column(name="save_path", attrName="savePath", label="save_path"),
		@Column(name="create_time", attrName="createTime", label="创建日期"),
//		@Column(includeEntity=DataEntity.class),
		@Column(name="file_url", attrName="fileUrl", label="全路径"),
	}, orderBy="a.speci_qualifications_code DESC"
)
public class AmSpeciQualifications extends DataEntity<AmSpeciQualifications> {
	
	private static final long serialVersionUID = 1L;
	private String speciQualificationsCode;		// 文件编号
	private String code;		// 样品进度code
	private String qualificationName;		// 文件名
	private String typeName;		// 文件类型
	private String profileSurfix;		// profile_surfix
	private String savePath;		// save_path
	private String fileUrl;		// 全路径
	private Date  createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public AmSpeciQualifications() {
		this(null);
	}

	public AmSpeciQualifications(String id){
		super(id);
	}
	
	public String getSpeciQualificationsCode() {
		return speciQualificationsCode;
	}

	public void setSpeciQualificationsCode(String speciQualificationsCode) {
		this.speciQualificationsCode = speciQualificationsCode;
	}
	
	@NotBlank(message="样品进度code不能为空")
	@Length(min=0, max=100, message="样品进度code长度不能超过 100 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="文件名长度不能超过 100 个字符")
	public String getQualificationName() {
		return qualificationName;
	}

	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	
	@Length(min=0, max=100, message="文件类型长度不能超过 100 个字符")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Length(min=0, max=100, message="profile_surfix长度不能超过 100 个字符")
	public String getProfileSurfix() {
		return profileSurfix;
	}

	public void setProfileSurfix(String profileSurfix) {
		this.profileSurfix = profileSurfix;
	}
	
	@Length(min=0, max=100, message="save_path长度不能超过 100 个字符")
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	@Length(min=0, max=200, message="全路径长度不能超过 200 个字符")
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}