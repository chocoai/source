/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.product.dao.ProductCategoryDao;
import com.jeesite.modules.asset.tianmao.dao.TbProductDao;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 同步淘宝商品列表Service
 * @author Jace
 * @version 2018-07-07
 */
@Service
@Transactional(readOnly=true)
public class TbProductService extends CrudService<TbProductDao, TbProduct> {
	@Autowired
	private TbProductDao dao;
	
	/**
	 * 获取单条数据
	 * @param tbProduct
	 * @return
	 */
	@Override
	public TbProduct get(TbProduct tbProduct) {
		return super.get(tbProduct);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbProduct
	 * @return
	 */
	@Override
	public Page<TbProduct> findPage(Page<TbProduct> page, TbProduct tbProduct) {
		page.setPageSize(50);
		return super.findPage(page, tbProduct);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProduct tbProduct) {
		super.save(tbProduct);
	}
	
	/**
	 * 更新状态
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProduct tbProduct) {
		super.updateStatus(tbProduct);
	}
	
	/**
	 * 删除数据
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProduct tbProduct) {
		super.delete(tbProduct);
	}


	@Transactional(readOnly=false)
	public void updateProductCategory(String procategoryCode,String proseriesCode,String numIid) {
		dao.updateProductCategory(procategoryCode,proseriesCode,numIid);
	}
	
}