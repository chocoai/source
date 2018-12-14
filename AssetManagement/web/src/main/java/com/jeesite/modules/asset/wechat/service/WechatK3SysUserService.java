/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.wechat.entity.WechatK3SysUser;
import com.jeesite.modules.asset.wechat.dao.WechatK3SysUserDao;

/**
 * js_wechat_k3_sys_userService
 * @author jace
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly=true)
public class WechatK3SysUserService extends CrudService<WechatK3SysUserDao, WechatK3SysUser> {
	
	/**
	 * 获取单条数据
	 * @param wechatK3SysUser
	 * @return
	 */
	@Override
	public WechatK3SysUser get(WechatK3SysUser wechatK3SysUser) {
		return super.get(wechatK3SysUser);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param wechatK3SysUser
	 * @return
	 */
	@Override
	public Page<WechatK3SysUser> findPage(Page<WechatK3SysUser> page, WechatK3SysUser wechatK3SysUser) {
		return super.findPage(page, wechatK3SysUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param wechatK3SysUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(WechatK3SysUser wechatK3SysUser) {
		super.save(wechatK3SysUser);
	}
	
	/**
	 * 更新状态
	 * @param wechatK3SysUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(WechatK3SysUser wechatK3SysUser) {
		super.updateStatus(wechatK3SysUser);
	}
	
	/**
	 * 删除数据
	 * @param wechatK3SysUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(WechatK3SysUser wechatK3SysUser) {
		super.delete(wechatK3SysUser);
	}
	
}