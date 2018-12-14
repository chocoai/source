/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.staff.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.Post;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.List;

/**
 * 员工资料表Entity
 * @author czy
 * @version 2018-04-26
 */
@Table(name="${_prefix}am_staff", alias="a", columns={
		@Column(name="belonged_dept", attrName="belongedDept", label="所属部门"),
		@Column(name="staff_code", attrName="staffCode", label="员工编号", isPK=true),
		@Column(name="staff_name", attrName="staffName", label="员工名称", queryType=QueryType.LIKE),
		@Column(name="ch_name", attrName="chName", label="中文名", queryType=QueryType.LIKE),
		@Column(name="position_level", attrName="positionLevel", label="职位级别"),
		@Column(name="sex", attrName="sex", label="性别"),
		@Column(name="phone_code", attrName="phoneCode", label="手机号码"),
		@Column(name="email", attrName="email", label="电子邮箱"),
		@Column(includeEntity=DataEntity.class),
	}, joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Office.class, alias="b",
		on="b.office_code = a.belonged_dept", attrName="office",
		columns={@Column(name="office_code", label="部门编码",isPK=true),
				@Column(name="office_name", label="部门名称",isQuery=false),
		}),
	}, orderBy="a.update_date DESC"
)

public class AmStaff extends DataEntity<AmStaff> {
	
	private static final long serialVersionUID = 1L;
	private String belongedDept;		// 所属部门
	private String staffCode;		// 员工编号
	private String staffName;		// 员工名称
	private String chName;		// 中文名
	private String positionLevel;		// 职位级别
	private String sex;		// 性别
	private String phoneCode;		// 手机号码
	private String email;		// 电子邮箱
	private Office office;
	private String postName;  // 列表页面岗位名称
	private AmStaffPost amStaffPost;
	private String postCode;

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public AmStaffPost getAmStaffPost() {
		return amStaffPost;
	}

	public void setAmStaffPost(AmStaffPost amStaffPost) {
		this.amStaffPost = amStaffPost;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	private AmStaffPost staffPost;

	public AmStaffPost getStaffPost() {
		return staffPost;
	}

	public void setStaffPost(AmStaffPost staffPost) {
		this.staffPost = staffPost;
	}

	private List<AmStaffPost> amStaffPostList = ListUtils.newArrayList(); // 关联岗位信息

	public Office getOffice() { return office; }

	public void setOffice(Office office) { this.office = office; }

	public AmStaff() {
		this(null);
	}

	public AmStaff(String id){
		super(id);
	}
	
	@NotBlank(message="所属部门不能为空")
	@Length(min=0, max=100, message="所属部门长度不能超过 100 个字符")
	public String getBelongedDept() {
		return belongedDept;
	}

	public void setBelongedDept(String belongedDept) {
		this.belongedDept = belongedDept;
	}
	
	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	@NotBlank(message="员工名称不能为空")
	@Length(min=0, max=100, message="员工名称长度不能超过 100 个字符")
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	@NotBlank(message="中文名不能为空")
	@Length(min=0, max=100, message="中文名长度不能超过 100 个字符")
	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}
	
	@NotBlank(message="职位级别不能为空")
	@Length(min=0, max=1, message="职位级别长度不能超过 1 个字符")
	public String getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	
	@NotBlank(message="性别不能为空")
	@Length(min=0, max=1, message="性别长度不能超过 1 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=100, message="手机号码长度不能超过 100 个字符")
	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	
	@Length(min=0, max=100, message="电子邮箱长度不能超过 100 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AmStaffPost> getAmStaffPostList() {
		return amStaffPostList;
	}

	public void setAmStaffPostList(List<AmStaffPost> amStaffPostList) {
		this.amStaffPostList = amStaffPostList;
	}
	public String[] getAmStaffPosts() {
		List<String> list = ListUtils.extractToList(amStaffPostList, "postCode");
		return list.toArray(new String[list.size()]);
	}

	public void setAmStaffPosts(String[] amStaffPosts) {
		for (String val : amStaffPosts){
			if (StringUtils.isNotBlank(val)){
				AmStaffPost e = new AmStaffPost();
				e.setPostCode(val);
				this.amStaffPostList.add(e);
			}
		}
	}
}