/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.TbItemDao;
import com.jeesite.modules.asset.tianmao.entity.TbItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * tb_itemService
 * @author jace
 * @version 2018-07-08
 */
@Service
@Transactional(readOnly=true)
public class TbItemService extends CrudService<TbItemDao, TbItem> {
	
	/**
	 * 获取单条数据
	 * @param tbItem
	 * @return
	 */
	@Override
	public TbItem get(TbItem tbItem) {
		return super.get(tbItem);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbItem
	 * @return
	 */
	@Override
	public Page<TbItem> findPage(Page<TbItem> page, TbItem tbItem) {
		return super.findPage(page, tbItem);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbItem tbItem) {
		super.save(tbItem);
	}
	
	/**
	 * 更新状态
	 * @param tbItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbItem tbItem) {
		super.updateStatus(tbItem);
	}
	
	/**
	 * 删除数据
	 * @param tbItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbItem tbItem) {
		super.delete(tbItem);
	}
	
}