/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.order.entity.FzNeigouOrder;
import com.jeesite.modules.fz.order.dao.FzNeigouOrderDao;

/**
 * 梵赞兑换订单表Service
 * @author easter
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly=true)
public class FzNeigouOrderService extends CrudService<FzNeigouOrderDao, FzNeigouOrder> {
	
	/**
	 * 获取单条数据
	 * @param fzNeigouOrder
	 * @return
	 */
	@Override
	public FzNeigouOrder get(FzNeigouOrder fzNeigouOrder) {
		return super.get(fzNeigouOrder);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzNeigouOrder
	 * @return
	 */
	@Override
	public Page<FzNeigouOrder> findPage(Page<FzNeigouOrder> page, FzNeigouOrder fzNeigouOrder) {
		return super.findPage(page, fzNeigouOrder);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzNeigouOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzNeigouOrder fzNeigouOrder) {
		super.save(fzNeigouOrder);
	}
	
	/**
	 * 更新状态
	 * @param fzNeigouOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzNeigouOrder fzNeigouOrder) {
		super.updateStatus(fzNeigouOrder);
	}
	
	/**
	 * 删除数据
	 * @param fzNeigouOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzNeigouOrder fzNeigouOrder) {
		super.delete(fzNeigouOrder);
	}
	
}