/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.asset.ding.dao.DingUserDepartmentDao;

/**
 * js_ding_user_departmentService
 * @author scarlett
 * @version 2018-09-22
 */
@Service
@Transactional(readOnly=true)
public class DingUserDepartmentService extends CrudService<DingUserDepartmentDao, DingUserDepartment> {
	
	/**
	 * 获取单条数据
	 * @param dingUserDepartment
	 * @return
	 */
	@Override
	public DingUserDepartment get(DingUserDepartment dingUserDepartment) {
		return super.get(dingUserDepartment);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param dingUserDepartment
	 * @return
	 */
	@Override
	public Page<DingUserDepartment> findPage(Page<DingUserDepartment> page, DingUserDepartment dingUserDepartment) {
		return super.findPage(page, dingUserDepartment);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param dingUserDepartment
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DingUserDepartment dingUserDepartment) {
		super.save(dingUserDepartment);
	}
	
	/**
	 * 更新状态
	 * @param dingUserDepartment
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DingUserDepartment dingUserDepartment) {
		super.updateStatus(dingUserDepartment);
	}
	
	/**
	 * 删除数据
	 * @param dingUserDepartment
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DingUserDepartment dingUserDepartment) {
		super.delete(dingUserDepartment);
	}

	public List<DingUserDepartment> selectByLeft() {
		return dao.selectByLeft();
	}

}