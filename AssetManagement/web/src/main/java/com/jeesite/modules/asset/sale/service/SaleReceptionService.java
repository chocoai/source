/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.sale.service;

import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.sale.entity.SaleReception;
import com.jeesite.modules.asset.sale.dao.SaleReceptionDao;

/**
 * 客户接待表Service
 * @author Scarlett
 * @version 2018-07-26
 */
@Service
@Transactional(readOnly=true)
public class SaleReceptionService extends CrudService<SaleReceptionDao, SaleReception> {
	
	/**
	 * 获取单条数据
	 * @param saleReception
	 * @return
	 */
	@Override
	public SaleReception get(SaleReception saleReception) {
		return super.get(saleReception);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param saleReception
	 * @return
	 */
	@Override
	public Page<SaleReception> findPage(Page<SaleReception> page, SaleReception saleReception) {
		page.setPageSize(50);
		if ("employee".equals(UserUtils.getUser().getUserType())) {
			saleReception.setOfficeCode(EmpUtils.getOffice().getOfficeCode());
		}
		return super.findPage(page, saleReception);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param saleReception
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SaleReception saleReception) {
		super.save(saleReception);
	}
	
	/**
	 * 更新状态
	 * @param saleReception
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SaleReception saleReception) {
		super.updateStatus(saleReception);
	}
	
	/**
	 * 删除数据
	 * @param saleReception
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SaleReception saleReception) {
		super.delete(saleReception);
	}
	
}