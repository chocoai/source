/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.message.entity.FzMessageManage;
import com.jeesite.modules.fz.message.dao.FzMessageManageDao;

/**
 * 梵赞消息推送Service
 * @author scarlett
 * @version 2018-10-24
 */
@Service
@Transactional(readOnly=true)
public class FzMessageManageService extends CrudService<FzMessageManageDao, FzMessageManage> {
	
	/**
	 * 获取单条数据
	 * @param fzMessageManage
	 * @return
	 */
	@Override
	public FzMessageManage get(FzMessageManage fzMessageManage) {
		return super.get(fzMessageManage);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzMessageManage
	 * @return
	 */
	@Override
	public Page<FzMessageManage> findPage(Page<FzMessageManage> page, FzMessageManage fzMessageManage) {
		return super.findPage(page, fzMessageManage);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzMessageManage
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzMessageManage fzMessageManage) {
		super.save(fzMessageManage);
	}
	
	/**
	 * 更新状态
	 * @param fzMessageManage
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzMessageManage fzMessageManage) {
		super.updateStatus(fzMessageManage);
	}
	
	/**
	 * 删除数据
	 * @param fzMessageManage
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzMessageManage fzMessageManage) {
		super.delete(fzMessageManage);
	}
	
}