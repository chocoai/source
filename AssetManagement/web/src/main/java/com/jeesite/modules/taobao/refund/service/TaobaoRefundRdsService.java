/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.refund.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.taobao.refund.entity.TaobaoRefundRds;
import com.jeesite.modules.taobao.refund.dao.TaobaoRefundRdsDao;

/**
 * taobao_refund_rdsService
 * @author scarlett
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly=true)
public class TaobaoRefundRdsService extends CrudService<TaobaoRefundRdsDao, TaobaoRefundRds> {
	
	/**
	 * 获取单条数据
	 * @param taobaoRefundRds
	 * @return
	 */
	@Override
	public TaobaoRefundRds get(TaobaoRefundRds taobaoRefundRds) {
		return super.get(taobaoRefundRds);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param taobaoRefundRds
	 * @return
	 */
	@Override
	public Page<TaobaoRefundRds> findPage(Page<TaobaoRefundRds> page, TaobaoRefundRds taobaoRefundRds) {
		return super.findPage(page, taobaoRefundRds);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taobaoRefundRds
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TaobaoRefundRds taobaoRefundRds) {
		super.save(taobaoRefundRds);
	}
	
	/**
	 * 更新状态
	 * @param taobaoRefundRds
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TaobaoRefundRds taobaoRefundRds) {
		super.updateStatus(taobaoRefundRds);
	}
	
	/**
	 * 删除数据
	 * @param taobaoRefundRds
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TaobaoRefundRds taobaoRefundRds) {
		super.delete(taobaoRefundRds);
	}
	
}