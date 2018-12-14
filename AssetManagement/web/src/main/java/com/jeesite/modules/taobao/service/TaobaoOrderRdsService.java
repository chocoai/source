/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.taobao.entity.TaobaoOrderRds;
import com.jeesite.modules.taobao.dao.TaobaoOrderRdsDao;

/**
 * taobao_order_rdsService
 * @author scarlett
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly=true)
public class TaobaoOrderRdsService extends CrudService<TaobaoOrderRdsDao, TaobaoOrderRds> {
	
	/**
	 * 获取单条数据
	 * @param taobaoOrderRds
	 * @return
	 */
	@Override
	public TaobaoOrderRds get(TaobaoOrderRds taobaoOrderRds) {
		return super.get(taobaoOrderRds);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param taobaoOrderRds
	 * @return
	 */
	@Override
	public Page<TaobaoOrderRds> findPage(Page<TaobaoOrderRds> page, TaobaoOrderRds taobaoOrderRds) {
		return super.findPage(page, taobaoOrderRds);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taobaoOrderRds
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TaobaoOrderRds taobaoOrderRds) {
		super.save(taobaoOrderRds);
	}
	
	/**
	 * 更新状态
	 * @param taobaoOrderRds
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TaobaoOrderRds taobaoOrderRds) {
		super.updateStatus(taobaoOrderRds);
	}
	
	/**
	 * 删除数据
	 * @param taobaoOrderRds
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TaobaoOrderRds taobaoOrderRds) {
		super.delete(taobaoOrderRds);
	}
	
}