/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.product.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.asset.product.dao.ProductCategoryDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 商品分类表Service
 * @author Scarlett
 * @version 2018-07-23
 */
@Service
@Transactional(readOnly=true)
public class ProductCategoryService extends TreeService<ProductCategoryDao, ProductCategory> {
	@Autowired
	private ProductCategoryDao dao;
	
	/**
	 * 获取单条数据
	 * @param productCategory
	 * @return
	 */
	@Override
	public ProductCategory get(ProductCategory productCategory) {
		return super.get(productCategory);
	}
	
	/**
	 * 查询列表数据
	 * @param productCategory
	 * @return
	 */
	@Override
	public List<ProductCategory> findList(ProductCategory productCategory) {
		return super.findList(productCategory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productCategory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductCategory productCategory) {
		super.save(productCategory);
		// 保存上传图片
		//FileUploadUtils.saveFileUpload(productCategory.getId(), "productCategory_image");
	}
	
	/**
	 * 更新状态
	 * @param productCategory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductCategory productCategory) {
		super.updateStatus(productCategory);
	}


	@Transactional(readOnly=true)
	public Integer selectByProcategoryStatus(String productCode) {
		return dao.selectByProcategoryStatus(productCode);

	}
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductCategory productCategory){
		dao.delete(productCategory.getProcategoryCode());
	}
	@Transactional(readOnly=true)
	public List<ProductCategory> findListOuter(){
		return  dao.findListOuter();
	}
	@Transactional(readOnly=true)
	public Integer findProductCategory(String name){
		return dao.findProductCategory(name);
	}
}