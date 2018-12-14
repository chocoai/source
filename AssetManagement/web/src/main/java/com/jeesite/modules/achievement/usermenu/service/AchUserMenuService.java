/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermenu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.usermenu.entity.AchUserMenu;
import com.jeesite.modules.achievement.usermenu.dao.AchUserMenuDao;

/**
 * 绩效卡用户菜单Service
 * @author Philip Guan
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly=true)
public class AchUserMenuService extends CrudService<AchUserMenuDao, AchUserMenu> {

	@Autowired
	private AchUserMenuDao achUserMenuDao;

	/**
	 * 获取单条数据
	 * @param achUserMenu
	 * @return
	 */
	@Override
	public AchUserMenu get(AchUserMenu achUserMenu) {
		return super.get(achUserMenu);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achUserMenu
	 * @return
	 */
	@Override
	public Page<AchUserMenu> findPage(Page<AchUserMenu> page, AchUserMenu achUserMenu) {
		return super.findPage(page, achUserMenu);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achUserMenu
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchUserMenu achUserMenu) {
		super.save(achUserMenu);
	}

	@Transactional(readOnly=false)
	public void save(String userId, List<AchUserMenu> list) {
		achUserMenuDao.delete(userId);
		if(list != null && list.size() > 0){
			achUserMenuDao.insertBatch(list);
		}

	}
	
	/**
	 * 更新状态
	 * @param achUserMenu
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchUserMenu achUserMenu) {
		super.updateStatus(achUserMenu);
	}
	
	/**
	 * 删除数据
	 * @param achUserMenu
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchUserMenu achUserMenu) {
		super.delete(achUserMenu);
	}
	
}