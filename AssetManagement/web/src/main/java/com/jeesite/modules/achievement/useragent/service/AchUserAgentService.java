/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.useragent.service;

import java.util.List;

import com.jeesite.modules.achievement.usermenu.entity.AchUserMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.useragent.entity.AchUserAgent;
import com.jeesite.modules.achievement.useragent.dao.AchUserAgentDao;

/**
 * 绩效卡用户代理Service
 * @author Philip Guan
 * @version 2018-11-23
 */
@Service
@Transactional(readOnly=true)
public class AchUserAgentService extends CrudService<AchUserAgentDao, AchUserAgent> {

	@Autowired
	private AchUserAgentDao achUserAgentDao;

	/**
	 * 获取单条数据
	 * @param achUserAgent
	 * @return
	 */
	@Override
	public AchUserAgent get(AchUserAgent achUserAgent) {
		return super.get(achUserAgent);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achUserAgent
	 * @return
	 */
	@Override
	public Page<AchUserAgent> findPage(Page<AchUserAgent> page, AchUserAgent achUserAgent) {
		return super.findPage(page, achUserAgent);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achUserAgent
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchUserAgent achUserAgent) {
		super.save(achUserAgent);
	}

	@Transactional(readOnly=false)
	public void save(String userId, List<AchUserAgent> list) {
		achUserAgentDao.delete(userId);
		if(list != null && list.size() > 0){
			achUserAgentDao.insertBatch(list);
		}

	}

	/**
	 * 更新状态
	 * @param achUserAgent
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchUserAgent achUserAgent) {
		super.updateStatus(achUserAgent);
	}
	
	/**
	 * 删除数据
	 * @param achUserAgent
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchUserAgent achUserAgent) {
		super.delete(achUserAgent);
	}
	
}