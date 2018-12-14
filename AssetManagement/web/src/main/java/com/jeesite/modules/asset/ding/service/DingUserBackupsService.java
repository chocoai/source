/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ding.entity.DingUserBackups;
import com.jeesite.modules.asset.ding.dao.DingUserBackupsDao;

/**
 * 钉钉用户表备份Service
 * @author len
 * @version 2018-10-31
 */
@Service
@Transactional(readOnly=true)
public class DingUserBackupsService extends CrudService<DingUserBackupsDao, DingUserBackups> {
	
	/**
	 * 获取单条数据
	 * @param dingUserBackups
	 * @return
	 */
	@Override
	public DingUserBackups get(DingUserBackups dingUserBackups) {
		return super.get(dingUserBackups);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param dingUserBackups
	 * @return
	 */
	@Override
	public Page<DingUserBackups> findPage(Page<DingUserBackups> page, DingUserBackups dingUserBackups) {
		return super.findPage(page, dingUserBackups);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param dingUserBackups
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DingUserBackups dingUserBackups) {
		super.save(dingUserBackups);
	}
	
	/**
	 * 更新状态
	 * @param dingUserBackups
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DingUserBackups dingUserBackups) {
		super.updateStatus(dingUserBackups);
	}
	
	/**
	 * 删除数据
	 * @param dingUserBackups
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DingUserBackups dingUserBackups) {
		super.delete(dingUserBackups);
	}

	
}