/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.service.TreeService;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import com.jeesite.modules.asset.product.dao.ProductSeriesDao;

/**
 * 商品系列表Service
 * @author Scarlett
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly=true)
public class ProductSeriesService extends TreeService<ProductSeriesDao, ProductSeries> {
	@Autowired
	private ProductSeriesDao dao;
	
	/**
	 * 获取单条数据
	 * @param productSeries
	 * @return
	 */
	@Override
	public ProductSeries get(ProductSeries productSeries) {
		return super.get(productSeries);
	}
	
	/**
	 * 查询列表数据
	 * @param productSeries
	 * @return
	 */
	@Override
	public List<ProductSeries> findList(ProductSeries productSeries) {
		return super.findList(productSeries);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productSeries
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductSeries productSeries) {
		super.save(productSeries);
		// 保存上传图片
		//FileUploadUtils.saveFileUpload(productSeries.getId(), "productSeries_image");
	}
	
	/**
	 * 更新状态
	 * @param productSeries
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductSeries productSeries) {
		super.updateStatus(productSeries);
	}
	
	/**
	 * 删除数据
	 * @param
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductSeries productSeries) {
		dao.delete(productSeries.getProseriesCode());
	}

	@Transactional(readOnly=true)
	public Integer selectByProseriesStatus(String proseriesCode) {
		return dao.selectByProseriesStatus(proseriesCode);

	}
	public Integer findProseries(String name){
		return dao.findProseries(name);
	}

	/**
	 * 以英文模糊查询数据
	 * @param series
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ProductSeries> selectByName (String series) {
		return dao.selectByName(series);
	}

}