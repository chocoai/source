/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.report.entity.FzStatisticalItem;
import com.jeesite.modules.fz.report.dao.FzStatisticalItemDao;

/**
 * 一级部门赞赏统计项目Service
 * @author easter
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly=true)
public class FzStatisticalItemService extends CrudService<FzStatisticalItemDao, FzStatisticalItem> {
	
	/**
	 * 获取单条数据
	 * @param fzStatisticalItem
	 * @return
	 */
	@Override
	public FzStatisticalItem get(FzStatisticalItem fzStatisticalItem) {
		return super.get(fzStatisticalItem);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzStatisticalItem
	 * @return
	 */
	@Override
	public Page<FzStatisticalItem> findPage(Page<FzStatisticalItem> page, FzStatisticalItem fzStatisticalItem) {
		return super.findPage(page, fzStatisticalItem);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzStatisticalItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzStatisticalItem fzStatisticalItem) {
		super.save(fzStatisticalItem);
	}
	
	/**
	 * 更新状态
	 * @param fzStatisticalItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzStatisticalItem fzStatisticalItem) {
		super.updateStatus(fzStatisticalItem);
	}
	
	/**
	 * 删除数据
	 * @param fzStatisticalItem
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzStatisticalItem fzStatisticalItem) {
		super.delete(fzStatisticalItem);
	}
	
}