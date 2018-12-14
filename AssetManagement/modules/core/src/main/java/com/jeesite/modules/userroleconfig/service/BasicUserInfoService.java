/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.userroleconfig.dao.BasicUserInfoDao;
import com.jeesite.modules.userroleconfig.entity.BasicUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户表Service
 * @author dwh
 * @version 2018-07-18
 */
@Service
@Transactional(readOnly=true)
public class BasicUserInfoService extends CrudService<BasicUserInfoDao, BasicUserInfo> {
	
	/**
	 * 获取单条数据
	 * @param basicUserInfo
	 * @return
	 */
	@Override
	public BasicUserInfo get(BasicUserInfo basicUserInfo) {
		return super.get(basicUserInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param basicUserInfo
	 * @return
	 */
	@Override
	public Page<BasicUserInfo> findPage(Page<BasicUserInfo> page, BasicUserInfo basicUserInfo) {
		return super.findPage(page, basicUserInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param basicUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BasicUserInfo basicUserInfo) {
		super.save(basicUserInfo);
	}
	
	/**
	 * 更新状态
	 * @param basicUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BasicUserInfo basicUserInfo) {
		super.updateStatus(basicUserInfo);
	}
	
	/**
	 * 删除数据
	 * @param basicUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BasicUserInfo basicUserInfo) {
		super.delete(basicUserInfo);
	}
	
}