/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationType;
import com.jeesite.modules.fz.appreciation.dao.FzAppreciationTypeDao;

/**
 * 赞赏类型Service
 * @author dwh
 * @version 2018-09-19
 */
@Service
@Transactional(readOnly=true)
public class FzAppreciationTypeService extends CrudService<FzAppreciationTypeDao, FzAppreciationType> {
	@Autowired
	private  FzAppreciationTypeDao fzAppreciationTypeDao;
	
	/**
	 * 获取单条数据
	 * @param fzAppreciationType
	 * @return
	 */
	@Override
	public FzAppreciationType get(FzAppreciationType fzAppreciationType) {
		return super.get(fzAppreciationType);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzAppreciationType
	 * @return
	 */
	@Override
	public Page<FzAppreciationType> findPage(Page<FzAppreciationType> page, FzAppreciationType fzAppreciationType) {
		return super.findPage(page, fzAppreciationType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzAppreciationType
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzAppreciationType fzAppreciationType) {
		super.save(fzAppreciationType);
	}
	
	/**
	 * 更新状态
	 * @param fzAppreciationType
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzAppreciationType fzAppreciationType) {
		super.updateStatus(fzAppreciationType);
	}
	
	/**
	 * 删除数据
	 * @param fzAppreciationType
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzAppreciationType fzAppreciationType) {
		fzAppreciationTypeDao.deleteByDb(fzAppreciationType.getTypeCode());
	}

    public List<FzAppreciationType> getListByPhon(String phon) {
		return  fzAppreciationTypeDao.getListByPhon(phon);
    }

	public List<FzAppreciationType> getListByPhons(List<String> phons) {
		return  fzAppreciationTypeDao.getListByPhons(phons);
	}
}