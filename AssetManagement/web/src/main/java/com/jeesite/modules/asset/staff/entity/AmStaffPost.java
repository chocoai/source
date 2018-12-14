/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.staff.entity;


import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.sys.entity.Post;

/**
 * 员工岗位表Entity
 * @author czy
 * @version 2018-04-27
 */
@Table(name="${_prefix}am_staff_post", alias="a", columns={
		@Column(name="staff_code", attrName="staffCode", label="员工编码", isPK=true),
		@Column(name="post_code", attrName="postCode", label="岗位编码", isPK=true),
	}, orderBy="a.staff_code DESC, a.post_code DESC"
)
public class AmStaffPost extends DataEntity<AmStaffPost> {
	
	private static final long serialVersionUID = 1L;
	private String staffCode;		// 员工编码
	private String postCode;		// 岗位编码
	
	public AmStaffPost() {
		this(null,null);
	}

	public AmStaffPost(String staffCode, String postCode){
		this.staffCode = staffCode;
		this.postCode = postCode;
	}
	
	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
}