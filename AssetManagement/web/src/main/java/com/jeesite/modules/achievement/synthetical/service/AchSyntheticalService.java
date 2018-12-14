/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.synthetical.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.synthetical.entity.AchSynthetical;
import com.jeesite.modules.achievement.synthetical.dao.AchSyntheticalDao;

/**
 * 绩效综合管理Service
 * @author len
 * @version 2018-11-16
 */
@Service
@Transactional(readOnly=true)
public class AchSyntheticalService extends CrudService<AchSyntheticalDao, AchSynthetical> {

	@Autowired
	private AchSyntheticalDao achSyntheticalDao;
	/**
	 * 获取单条数据
	 * @param achSynthetical
	 * @return
	 */
	@Override
	public AchSynthetical get(AchSynthetical achSynthetical) {
		return super.get(achSynthetical);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achSynthetical
	 * @return
	 */
	@Override
	public Page<AchSynthetical> findPage(Page<AchSynthetical> page, AchSynthetical achSynthetical) {
		return super.findPage(page, achSynthetical);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achSynthetical
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchSynthetical achSynthetical) {
		super.save(achSynthetical);
	}
	
	/**
	 * 更新状态
	 * @param achSynthetical
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchSynthetical achSynthetical) {
		super.updateStatus(achSynthetical);
	}
	
	/**
	 * 删除数据
	 * @param achSynthetical
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchSynthetical achSynthetical) {
		achSyntheticalDao.deleteDb(achSynthetical.getSyntheticalCode());
//		super.delete(achSynthetical);
	}

	@Transactional(readOnly = false)
	public void enable(String syntheticalCode) {
		achSyntheticalDao.enable(syntheticalCode);
	}
}