/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.staff.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.staff.entity.AmStaffPost;
import com.jeesite.modules.asset.staff.dao.AmStaffPostDao;

/**
 * 员工岗位表Service
 * @author czy
 * @version 2018-04-27
 */
@Service
@Transactional(readOnly=true)
public class AmStaffPostService extends CrudService<AmStaffPostDao, AmStaffPost> {
	
	/**
	 * 获取单条数据
	 * @param amStaffPost
	 * @return
	 */
	@Override
	public AmStaffPost get(AmStaffPost amStaffPost) {
		return super.get(amStaffPost);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amStaffPost
	 * @return
	 */
	@Override
	public Page<AmStaffPost> findPage(Page<AmStaffPost> page, AmStaffPost amStaffPost) {
		return super.findPage(page, amStaffPost);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amStaffPost
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmStaffPost amStaffPost) {
		super.save(amStaffPost);
	}
	
	/**
	 * 更新状态
	 * @param amStaffPost
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmStaffPost amStaffPost) {
		super.updateStatus(amStaffPost);
	}
	
	/**
	 * 删除数据
	 * @param amStaffPost
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmStaffPost amStaffPost) {
		super.delete(amStaffPost);
	}
	
}