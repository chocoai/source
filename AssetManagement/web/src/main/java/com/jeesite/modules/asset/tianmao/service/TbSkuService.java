/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.dao.TbSkuDao;

/**
 * 商品-SKU管理Service
 * @author jace
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly=true)
public class TbSkuService extends CrudService<TbSkuDao, TbSku> {
	@Autowired
	private TbSkuDao tbSkuDao;
	/**
	 * 获取单条数据
	 * @param tbSku
	 * @return
	 */
	@Override
	public TbSku get(TbSku tbSku) {
		return super.get(tbSku);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbSku
	 * @return
	 */
	@Override
	public Page<TbSku> findPage(Page<TbSku> page, TbSku tbSku) {
		page.setPageSize(30);
		return super.findPage(page, tbSku);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbSku
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbSku tbSku) {
		super.save(tbSku);
	}
	
	/**
	 * 更新状态
	 * @param tbSku
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbSku tbSku) {
		super.updateStatus(tbSku);
	}
	
	/**
	 * 删除数据
	 * @param tbSku
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbSku tbSku) {
		super.delete(tbSku);
	}

	/**
	 * 获取sku表数据
	 * @param numIid
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TbSku> getSku (Long numIid) {
		return tbSkuDao.getSku(numIid);
	}

	@Transactional(readOnly = true)
	public List<TbSku> findByNumIid(String numIid){
		return tbSkuDao.findByNumIid(numIid);
	}

	/**
	 * 查询表头卖家昵称为saladliang的SKU信息
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TbSku> selectSku() {
		return tbSkuDao.selectSku();
	}

	/**
	 * 根据skuid更新真实价格
	 */
	@Transactional(readOnly = false)
	public void savePrice (Long skuId, String realPrice) {
		tbSkuDao.updatePrice(skuId, realPrice);
	}

	@Transactional(readOnly = true)
	public TbSku selectBySkuId(String skuId) {
		return tbSkuDao.selectBySkuId(skuId);
	}

	/**
	 * 获取店铺（卖家昵称），SKU，真实售价
	 * @return
	 */
	public List<TbSku> getSkuAndPrice() {
		return tbSkuDao.getSkuAndPrice();
	}
}