/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.dispute.dao.AmDisputeImgDao;
import com.jeesite.modules.asset.dispute.entity.AmDisputeImg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物流纠纷单图片Service
 * @author czy
 * @version 2018-06-19
 */
@Service
@Transactional(readOnly=true)
public class AmDisputeImgService extends CrudService<AmDisputeImgDao, AmDisputeImg> {
	/**
	 * 获取单条数据
	 * @param amDisputeImg
	 * @return
	 */
	@Override
	public AmDisputeImg get(AmDisputeImg amDisputeImg) {
		return super.get(amDisputeImg);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amDisputeImg
	 * @return
	 */
	@Override
	public Page<AmDisputeImg> findPage(Page<AmDisputeImg> page, AmDisputeImg amDisputeImg) {
		return super.findPage(page, amDisputeImg);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amDisputeImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmDisputeImg amDisputeImg) {
		super.save(amDisputeImg);
	}
	
	/**
	 * 更新状态
	 * @param amDisputeImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmDisputeImg amDisputeImg) {
		super.updateStatus(amDisputeImg);
	}
	
	/**
	 * 删除数据
	 * @param amDisputeImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmDisputeImg amDisputeImg) {
		super.delete(amDisputeImg);
	}
	
}