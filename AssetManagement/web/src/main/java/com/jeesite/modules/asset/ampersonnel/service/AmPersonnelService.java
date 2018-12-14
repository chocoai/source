/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ampersonnel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ampersonnel.entity.AmPersonnel;
import com.jeesite.modules.asset.ampersonnel.dao.AmPersonnelDao;

/**
 * 有效人员维护表Service
 * @author mclaran
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly=true)
public class AmPersonnelService extends CrudService<AmPersonnelDao, AmPersonnel> {

	@Autowired
	private AmPersonnelDao amPersonnelDao;
	/**
	 * 获取单条数据
	 * @param amPersonnel
	 * @return
	 */
	@Override
	public AmPersonnel get(AmPersonnel amPersonnel) {
		return super.get(amPersonnel);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amPersonnel
	 * @return
	 */
	@Override
	public Page<AmPersonnel> findPage(Page<AmPersonnel> page, AmPersonnel amPersonnel) {
		return super.findPage(page, amPersonnel);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amPersonnel
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmPersonnel amPersonnel) {
		super.save(amPersonnel);
	}
	
	/**
	 * 更新状态
	 * @param amPersonnel
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmPersonnel amPersonnel) {
		super.updateStatus(amPersonnel);
	}
	
	/**
	 * 删除数据
	 * @param amPersonnel
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmPersonnel amPersonnel) {
		super.delete(amPersonnel);
	}

	/**
	 * 根据电话号码查询用户是否有权登录
	 *
	 */
    public int check(String phone) {
    	return amPersonnelDao.check(phone);
    }
}