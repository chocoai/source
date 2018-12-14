/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.department.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.department.entity.DepartmentTok3;
import com.jeesite.modules.asset.department.dao.DepartmentTok3Dao;

/**
 * 部门关联K3关系表Service
 * @author Scarlett
 * @version 2018-08-04
 */
@Service
@Transactional(readOnly=true)
public class DepartmentTok3Service extends CrudService<DepartmentTok3Dao, DepartmentTok3> {
	@Autowired
	private DepartmentTok3Dao dao;
	/**
	 * 获取单条数据
	 * @param departmentTok3
	 * @return
	 */
	@Override
	public DepartmentTok3 get(DepartmentTok3 departmentTok3) {
		return super.get(departmentTok3);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param departmentTok3
	 * @return
	 */
	@Override
	public Page<DepartmentTok3> findPage(Page<DepartmentTok3> page, DepartmentTok3 departmentTok3) {
		return super.findPage(page, departmentTok3);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param departmentTok3
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DepartmentTok3 departmentTok3) {
		super.save(departmentTok3);
	}
	
	/**
	 * 更新状态
	 * @param departmentTok3
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DepartmentTok3 departmentTok3) {
		super.updateStatus(departmentTok3);
	}
	
	/**
	 * 删除数据
	 * @param departmentTok3
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DepartmentTok3 departmentTok3) {
		super.delete(departmentTok3);
	}

	/**
	 * 物理删除数据
	 */
	@Transactional(readOnly=false)
	public  void deleteData(String departmentCode){
		dao.deleteData(departmentCode);
	}
	
}