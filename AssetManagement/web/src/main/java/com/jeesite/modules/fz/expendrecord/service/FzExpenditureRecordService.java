/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.expendrecord.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.expendrecord.entity.FzExpenditureRecord;
import com.jeesite.modules.fz.expendrecord.dao.FzExpenditureRecordDao;

/**
 * 梵钻支出表Service
 * @author len
 * @version 2018-12-18
 */
@Service
@Transactional(readOnly=true)
public class FzExpenditureRecordService extends CrudService<FzExpenditureRecordDao, FzExpenditureRecord> {
	
	/**
	 * 获取单条数据
	 * @param fzExpenditureRecord
	 * @return
	 */
	@Override
	public FzExpenditureRecord get(FzExpenditureRecord fzExpenditureRecord) {
		return super.get(fzExpenditureRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzExpenditureRecord
	 * @return
	 */
	@Override
	public Page<FzExpenditureRecord> findPage(Page<FzExpenditureRecord> page, FzExpenditureRecord fzExpenditureRecord) {
		return super.findPage(page, fzExpenditureRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzExpenditureRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzExpenditureRecord fzExpenditureRecord) {
		super.save(fzExpenditureRecord);
	}
	
	/**
	 * 更新状态
	 * @param fzExpenditureRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzExpenditureRecord fzExpenditureRecord) {
		super.updateStatus(fzExpenditureRecord);
	}
	
	/**
	 * 删除数据
	 * @param fzExpenditureRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzExpenditureRecord fzExpenditureRecord) {
		super.delete(fzExpenditureRecord);
	}
	
}