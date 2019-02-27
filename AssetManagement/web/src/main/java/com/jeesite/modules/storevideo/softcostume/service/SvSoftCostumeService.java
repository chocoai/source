/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.softcostume.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.softcostume.entity.SvSoftCostume;
import com.jeesite.modules.storevideo.softcostume.dao.SvSoftCostumeDao;

/**
 * 软装方案管理Service
 * @author len
 * @version 2019-02-26
 */
@Service
@Transactional(readOnly=true)
public class SvSoftCostumeService extends CrudService<SvSoftCostumeDao, SvSoftCostume> {
	
	/**
	 * 获取单条数据
	 * @param svSoftCostume
	 * @return
	 */
	@Override
	public SvSoftCostume get(SvSoftCostume svSoftCostume) {
		return super.get(svSoftCostume);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svSoftCostume
	 * @return
	 */
	@Override
	public Page<SvSoftCostume> findPage(Page<SvSoftCostume> page, SvSoftCostume svSoftCostume) {
		return super.findPage(page, svSoftCostume);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svSoftCostume
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvSoftCostume svSoftCostume) {
		super.save(svSoftCostume);
	}
	
	/**
	 * 更新状态
	 * @param svSoftCostume
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvSoftCostume svSoftCostume) {
		super.updateStatus(svSoftCostume);
	}
	
	/**
	 * 删除数据
	 * @param svSoftCostume
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvSoftCostume svSoftCostume) {
		super.delete(svSoftCostume);
	}
	
}