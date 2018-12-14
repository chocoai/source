/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.TbTianmaoItemsDao;
import com.jeesite.modules.asset.tianmao.entity.TbTianmaoItems;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * tb_tianmao_itemsService
 * @author jace
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly=true)
public class TbTianmaoItemsService extends CrudService<TbTianmaoItemsDao, TbTianmaoItems> {
	
	/**
	 * 获取单条数据
	 * @param tbTianmaoItems
	 * @return
	 */
	@Override
	public TbTianmaoItems get(TbTianmaoItems tbTianmaoItems) {
		return super.get(tbTianmaoItems);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbTianmaoItems
	 * @return
	 */
	@Override
	public Page<TbTianmaoItems> findPage(Page<TbTianmaoItems> page, TbTianmaoItems tbTianmaoItems) {
		return super.findPage(page, tbTianmaoItems);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbTianmaoItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbTianmaoItems tbTianmaoItems) {
		super.save(tbTianmaoItems);
	}
	
	/**
	 * 更新状态
	 * @param tbTianmaoItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbTianmaoItems tbTianmaoItems) {
		super.updateStatus(tbTianmaoItems);
	}
	
	/**
	 * 删除数据
	 * @param tbTianmaoItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbTianmaoItems tbTianmaoItems) {
		super.delete(tbTianmaoItems);
	}
	
}