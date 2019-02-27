/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.entity;

import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import net.sf.json.JSONObject;
import org.hibernate.validator.constraints.Length;

import java.util.Map;
import java.util.function.Consumer;

/**
 * js_ding_departmentEntity
 * @author scarlett
 * @version 2018-09-20
 */
@Table(name="${_prefix}ding_department", alias="a", columns={
		@Column(name="department_id", attrName="departmentId", label="部门id", isPK=true),
		@Column(name="name", attrName="name", label="部门名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(name="parentid", attrName="parentid", label="父部门id，根部门为1"),
		@Column(name="order", attrName="order", label="在父部门中的次序值"),
		@Column(name="create_dept_group", attrName="createDeptGroup", label="是否同步创建一个关联此部门的企业群"),
		@Column(name="auto_add_user", attrName="autoAddUser", label="是否有新人加入部门会自动加入该群"),
		@Column(name="dept_hiding", attrName="deptHiding", label="是否隐藏部门, true表示隐藏, false表示显示"),
		@Column(name="dept_permits", attrName="deptPermits", label="可以查看指定隐藏部门的其他部门列表"),
		@Column(name="user_permits", attrName="userPermits", label="可以查看指定隐藏部门的其他人员列表"),
		@Column(name="outer_dept", attrName="outerDept", label="outer_dept"),
		@Column(name="outer_permit_pepts", attrName="outerPermitPepts", label="本部门的员工仅可见员工自己为true时"),
		@Column(name="outer_permit_users", attrName="outerPermitUsers", label="本部门的员工仅可见员工自己为true时"),
		@Column(name="org_dept_owner", attrName="orgDeptOwner", label="企业群群主"),
		@Column(name="dept_manager_userid_list", attrName="deptManagerUseridList", label="部门的主管列表"),
		@Column(name="source_identifier", attrName="sourceIdentifier", label="部门标识字段"),
		@Column(name="group_contain_subdept", attrName="groupContainSubdept", label="部门群是否包含子部门"),
		@Column(name="user_count", attrName="userCount", label="本部门及其下子部门人数"),
		@Column(includeEntity=TreeEntity.class),
	}, orderBy="a.tree_sorts, a.department_id"
)
public class DingDepartment extends TreeEntity<DingDepartment> {
	
	private static final long serialVersionUID = 1L;
	private String departmentId;		// 部门id
	private String name;		// 部门名称
	private String parentid;		// 父部门id，根部门为1
	private String order;		// 在父部门中的次序值
	private String createDeptGroup;		// 是否同步创建一个关联此部门的企业群
	private String autoAddUser;		// 是否有新人加入部门会自动加入该群
	private String deptHiding;		// 是否隐藏部门, true表示隐藏, false表示显示
	private String deptPermits;		// 可以查看指定隐藏部门的其他部门列表
	private String userPermits;		// 可以查看指定隐藏部门的其他人员列表
	private String outerDept;		// outer_dept
	private String outerPermitPepts;		// 本部门的员工仅可见员工自己为true时
	private String outerPermitUsers;		// 本部门的员工仅可见员工自己为true时
	private String orgDeptOwner;		// 企业群群主
	private String deptManagerUseridList;		// 部门的主管列表
	private String sourceIdentifier;		// 部门标识字段
	private String departmentName;
	private Long userCount;
	private String groupContainSubdept;		// 部门群是否包含子部门
	private String priDeptName;//上级部门名称
	private String managerName;//部门主管名字
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }


	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPriDeptName() {
		return priDeptName;
	}

	public void setPriDeptName(String PriDeptName) {
		this.priDeptName = PriDeptName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public DingDepartment() {
		this(null);
	}
	public DingDepartment(String departmentId, Integer userCount) {
		this(departmentId);
		this.setDepartmentId(departmentId);
		this.setUserCount(Long.valueOf(userCount));
	}

	public DingDepartment(String id){
		super(id);
	}
	
	@Override
	public DingDepartment getParent() {
		return parent;
	}

	@Override
	public void setParent(DingDepartment parent) {
		this.parent = parent;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Length(min=0, max=225, message="部门名称长度不能超过 225 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=225, message="父部门id，根部门为1长度不能超过 225 个字符")
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@Length(min=0, max=225, message="在父部门中的次序值长度不能超过 225 个字符")
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	@Length(min=0, max=10, message="是否同步创建一个关联此部门的企业群长度不能超过 10 个字符")
	public String getCreateDeptGroup() {
		return createDeptGroup;
	}

	public void setCreateDeptGroup(String createDeptGroup) {
		this.createDeptGroup = createDeptGroup;
	}
	
	@Length(min=0, max=10, message="是否有新人加入部门会自动加入该群长度不能超过 10 个字符")
	public String getAutoAddUser() {
		return autoAddUser;
	}

	public void setAutoAddUser(String autoAddUser) {
		this.autoAddUser = autoAddUser;
	}
	
	@Length(min=0, max=10, message="是否隐藏部门, true表示隐藏, false表示显示长度不能超过 10 个字符")
	public String getDeptHiding() {
		return deptHiding;
	}

	public void setDeptHiding(String deptHiding) {
		this.deptHiding = deptHiding;
	}
	
	@Length(min=0, max=500, message="可以查看指定隐藏部门的其他部门列表长度不能超过 500 个字符")
	public String getDeptPermits() {
		return deptPermits;
	}

	public void setDeptPermits(String deptPermits) {
		this.deptPermits = deptPermits;
	}
	
	@Length(min=0, max=1000, message="可以查看指定隐藏部门的其他人员列表长度不能超过 1000 个字符")
	public String getUserPermits() {
		return userPermits;
	}

	public void setUserPermits(String userPermits) {
		this.userPermits = userPermits;
	}
	
	@Length(min=0, max=10, message="outer_dept长度不能超过 10 个字符")
	public String getOuterDept() {
		return outerDept;
	}

	public void setOuterDept(String outerDept) {
		this.outerDept = outerDept;
	}
	
	@Length(min=0, max=1000, message="本部门的员工仅可见员工自己为true时长度不能超过 1000 个字符")
	public String getOuterPermitPepts() {
		return outerPermitPepts;
	}

	public void setOuterPermitPepts(String outerPermitPepts) {
		this.outerPermitPepts = outerPermitPepts;
	}
	
	@Length(min=0, max=1000, message="本部门的员工仅可见员工自己为true时长度不能超过 1000 个字符")
	public String getOuterPermitUsers() {
		return outerPermitUsers;
	}

	public void setOuterPermitUsers(String outerPermitUsers) {
		this.outerPermitUsers = outerPermitUsers;
	}
	
	@Length(min=0, max=225, message="企业群群主长度不能超过 225 个字符")
	public String getOrgDeptOwner() {
		return orgDeptOwner;
	}

	public void setOrgDeptOwner(String orgDeptOwner) {
		this.orgDeptOwner = orgDeptOwner;
	}
	
	@Length(min=0, max=500, message="部门的主管列表长度不能超过 500 个字符")
	public String getDeptManagerUseridList() {
		return deptManagerUseridList;
	}

	public void setDeptManagerUseridList(String deptManagerUseridList) {
		this.deptManagerUseridList = deptManagerUseridList;
	}
	
	@Length(min=0, max=500, message="部门标识字段长度不能超过 500 个字符")
	public String getSourceIdentifier() {
		return sourceIdentifier;
	}

	public void setSourceIdentifier(String sourceIdentifier) {
		this.sourceIdentifier = sourceIdentifier;
	}
	
	@Length(min=0, max=10, message="部门群是否包含子部门长度不能超过 10 个字符")
	public String getGroupContainSubdept() {
		return groupContainSubdept;
	}

	public void setGroupContainSubdept(String groupContainSubdept) {
		this.groupContainSubdept = groupContainSubdept;
	}

}