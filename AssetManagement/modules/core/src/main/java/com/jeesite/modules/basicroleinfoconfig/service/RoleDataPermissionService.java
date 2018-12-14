/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.basicroleinfoconfig.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.basicroleinfoconfig.dao.RoleDataPermissionDao;
import com.jeesite.modules.basicroleinfoconfig.entity.RoleDataPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色数据权限Service
 * @author dwh
 * @version 2018-07-26
 */
@Service
@Transactional(readOnly=true)
public class RoleDataPermissionService extends CrudService<RoleDataPermissionDao, RoleDataPermission> {
	@Autowired
	private RoleDataPermissionDao roleDataPermissionDao;
	/**
	 * 获取单条数据
	 * @param roleDataPermission
	 * @return
	 */
	@Override
	public RoleDataPermission get(RoleDataPermission roleDataPermission) {
		return super.get(roleDataPermission);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param roleDataPermission
	 * @return
	 */
	@Override
	public Page<RoleDataPermission> findPage(Page<RoleDataPermission> page, RoleDataPermission roleDataPermission) {
		return super.findPage(page, roleDataPermission);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param roleDataPermission
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(RoleDataPermission roleDataPermission) {
		super.save(roleDataPermission);
	}

	/**
	 * 更新状态
	 * @param roleDataPermission
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(RoleDataPermission roleDataPermission) {
		super.updateStatus(roleDataPermission);
	}

	/**
	 * 删除数据
	 * @param roleDataPermission
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(RoleDataPermission roleDataPermission) {
		super.delete(roleDataPermission);
	}

	public List<RoleDataPermission> getListDataByRoleCode(String roleCode) {
		List<RoleDataPermission> userDataPermission=roleDataPermissionDao.getListByRoleCode(roleCode);
		return  userDataPermission;
	}
}