/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wechat.service;

import java.util.List;

import com.jeesite.modules.sys.utils.RoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.wechat.entity.WechatK3User;
import com.jeesite.modules.asset.wechat.dao.WechatK3UserDao;

/**
 * js_wechat_k3_userService
 * @author jace
 * @version 2018-08-01
 */
@Service
@Transactional(readOnly=true)
public class WechatK3UserService extends CrudService<WechatK3UserDao, WechatK3User> {
	@Autowired
	private  WechatK3UserDao wechatK3UserDao;
	
	/**
	 * 获取单条数据
	 * @param wechatK3User
	 * @return
	 */
	@Override
	public WechatK3User get(WechatK3User wechatK3User) {
		return super.get(wechatK3User);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param wechatK3User
	 * @return
	 */
	@Override
	public Page<WechatK3User> findPage(Page<WechatK3User> page, WechatK3User wechatK3User) {
		return super.findPage(page, wechatK3User);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param wechatK3User
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(WechatK3User wechatK3User) {
		super.save(wechatK3User);
	}
	
	/**
	 * 更新状态
	 * @param wechatK3User
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(WechatK3User wechatK3User) {
		super.updateStatus(wechatK3User);
	}
	
	/**
	 * 删除数据
	 * @param wechatK3User
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(WechatK3User wechatK3User) {
		super.delete(wechatK3User);
	}

	public WechatK3User getWechatK3UserByOenId(String openId) {
		return wechatK3UserDao.getWechatK3UserByOenId(openId);
	}

	public List<String> getUserRoleByLoginCode(String loginCode) {
		return wechatK3UserDao.getUserRoleByLoginCode(loginCode);
	}
}