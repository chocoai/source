/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.wechat.entity.WechatK3Mechanism;
import com.jeesite.modules.asset.wechat.dao.WechatK3MechanismDao;

/**
 * js_wechat_k3_mechanismService
 * @author jace
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly=true)
public class WechatK3MechanismService extends CrudService<WechatK3MechanismDao, WechatK3Mechanism> {
	
	/**
	 * 获取单条数据
	 * @param wechatK3Mechanism
	 * @return
	 */
	@Override
	public WechatK3Mechanism get(WechatK3Mechanism wechatK3Mechanism) {
		return super.get(wechatK3Mechanism);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param wechatK3Mechanism
	 * @return
	 */
	@Override
	public Page<WechatK3Mechanism> findPage(Page<WechatK3Mechanism> page, WechatK3Mechanism wechatK3Mechanism) {
		return super.findPage(page, wechatK3Mechanism);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param wechatK3Mechanism
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(WechatK3Mechanism wechatK3Mechanism) {
		super.save(wechatK3Mechanism);
	}
	
	/**
	 * 更新状态
	 * @param wechatK3Mechanism
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(WechatK3Mechanism wechatK3Mechanism) {
		super.updateStatus(wechatK3Mechanism);
	}
	
	/**
	 * 删除数据
	 * @param wechatK3Mechanism
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(WechatK3Mechanism wechatK3Mechanism) {
		super.delete(wechatK3Mechanism);
	}
	
}