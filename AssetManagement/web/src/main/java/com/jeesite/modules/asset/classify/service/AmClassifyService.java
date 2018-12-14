/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.classify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.service.TreeService;
import com.jeesite.modules.asset.classify.entity.AmClassify;
import com.jeesite.modules.asset.classify.dao.AmClassifyDao;

/**
 * 资产分类表Service
 * @author czy
 * @version 2018-04-24
 */
@Service
@Transactional(readOnly=true)
public class AmClassifyService extends TreeService<AmClassifyDao, AmClassify> {

	@Autowired
	private AmClassifyDao amClassifyDao;
	/**
	 * 获取单条数据
	 * @param amClassify
	 * @return
	 */
	@Override
	public AmClassify get(AmClassify amClassify) {
		return super.get(amClassify);
	}
	
	/**
	 * 查询列表数据
	 * @param amClassify
	 * @return
	 */
	@Override
	public List<AmClassify> findList(AmClassify amClassify) {
		return super.findList(amClassify);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amClassify
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmClassify amClassify) {
		super.save(amClassify);
	}
	
	/**
	 * 更新状态
	 * @param amClassify
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmClassify amClassify) {
		amClassifyDao.updateStatus(amClassify);
	}
	
	/**
	 * 删除数据
	 * @param amClassify
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmClassify amClassify) {
	//	super.delete(amClassify);
		amClassifyDao.deleteDb(amClassify.getClassifyCode());
		if (amClassify != null && amClassify.getParent() != null) {
			dao.updateTreeLeaf(amClassify.getParent());
		}
	}
	
}