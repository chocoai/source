/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.order.entity.FzNeigouOrderItem;
import com.jeesite.modules.fz.order.dao.FzNeigouOrderItemDao;

/**
 * 梵赞内购订单商品明细表Service
 * @author easter
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly=true)
public class FzNeigouOrderItemService extends CrudService<FzNeigouOrderItemDao, FzNeigouOrderItem> {
	
	/**
	 * 获取单条数据
	 * @param fzNeigouOrderItem
	 * @return
	 */
	@Override
	public FzNeigouOrderItem get(FzNeigouOrderItem fzNeigouOrderItem) {
		return super.get(fzNeigouOrderItem);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzNeigouOrderItem
	 * @return
	 */
	@Override
	public Page<FzNeigouOrderItem> findPage(Page<FzNeigouOrderItem> page, FzNeigouOrderItem fzNeigouOrderItem) {
		return super.findPage(page, fzNeigouOrderItem);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzNeigouOrderItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzNeigouOrderItem fzNeigouOrderItem) {
		super.save(fzNeigouOrderItem);
	}
	
	/**
	 * 更新状态
	 * @param fzNeigouOrderItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzNeigouOrderItem fzNeigouOrderItem) {
		super.updateStatus(fzNeigouOrderItem);
	}
	
	/**
	 * 删除数据
	 * @param fzNeigouOrderItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzNeigouOrderItem fzNeigouOrderItem) {
		super.delete(fzNeigouOrderItem);
	}
	
}