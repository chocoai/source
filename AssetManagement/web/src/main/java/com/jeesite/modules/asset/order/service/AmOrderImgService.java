/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.order.entity.AmOrderImg;
import com.jeesite.modules.asset.order.dao.AmOrderImgDao;

/**
 * 订单图片表Service
 * @author len
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly=true)
public class AmOrderImgService extends CrudService<AmOrderImgDao, AmOrderImg> {

	@Autowired
	private AmOrderImgDao amOrderImgDao;
	/**
	 * 获取单条数据
	 * @param amOrderImg
	 * @return
	 */
	@Override
	public AmOrderImg get(AmOrderImg amOrderImg) {
		return super.get(amOrderImg);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amOrderImg
	 * @return
	 */
	@Override
	public Page<AmOrderImg> findPage(Page<AmOrderImg> page, AmOrderImg amOrderImg) {
		return super.findPage(page, amOrderImg);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amOrderImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmOrderImg amOrderImg) {
		super.save(amOrderImg);
	}
	
	/**
	 * 更新状态
	 * @param amOrderImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmOrderImg amOrderImg) {
		super.updateStatus(amOrderImg);
	}
	
	/**
	 * 删除数据
	 * @param amOrderImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmOrderImg amOrderImg) {
		super.delete(amOrderImg);
	}

	@Transactional(readOnly = false)
	public void updateImgStatus(String imgCode) {
		amOrderImgDao.updateImgStatus(imgCode);
	}

	/**
	 * 添加凭证图片
	 * @param imgList
	 */
	@Transactional(readOnly = false)
	public void insertBatch(List<AmOrderImg> imgList ) {
		amOrderImgDao.insertBatch(imgList);
	}

	@Transactional(readOnly = false)
	public void updateBatch(List<String> list ) {
		amOrderImgDao.updateBatch(list);
	}
}