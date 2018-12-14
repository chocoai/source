/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.taobao.entity.TaobaoEvaluate;
import com.jeesite.modules.taobao.dao.TaobaoEvaluateDao;

/**
 * 图片评价表Service
 * @author len
 * @version 2018-11-01
 */
@Service
@Transactional(readOnly=true)
public class TaobaoEvaluateService extends CrudService<TaobaoEvaluateDao, TaobaoEvaluate> {

	@Autowired
	private TaobaoEvaluateDao taobaoEvaluateDao;
	/**
	 * 获取单条数据
	 * @param taobaoEvaluate
	 * @return
	 */
	@Override
	public TaobaoEvaluate get(TaobaoEvaluate taobaoEvaluate) {
		return super.get(taobaoEvaluate);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param taobaoEvaluate
	 * @return
	 */
	@Override
	public Page<TaobaoEvaluate> findPage(Page<TaobaoEvaluate> page, TaobaoEvaluate taobaoEvaluate) {
		return super.findPage(page, taobaoEvaluate);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taobaoEvaluate
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TaobaoEvaluate taobaoEvaluate) {
		super.save(taobaoEvaluate);
	}
	
	/**
	 * 更新状态
	 * @param taobaoEvaluate
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TaobaoEvaluate taobaoEvaluate) {
		super.updateStatus(taobaoEvaluate);
	}
	
	/**
	 * 删除数据
	 * @param taobaoEvaluate
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TaobaoEvaluate taobaoEvaluate) {
		super.delete(taobaoEvaluate);
	}

	public TaobaoEvaluate getEvaluate(String productId, Long publishDate, String authorName) {
		return taobaoEvaluateDao.getEvaluate(productId, publishDate, authorName);
	}
}