/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.service;

import java.util.List;
import java.util.Optional;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.service.QueryService;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.screen.entity.ProductData;
import com.jeesite.modules.asset.screen.entity.ScreenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.screen.entity.ScreenProduct;
import com.jeesite.modules.asset.screen.dao.ScreenProductDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 零售家产品详情Service
 * @author len
 * @version 2019-01-08
 */
@Service
@Transactional(readOnly=true)
public class ScreenProductService extends CrudService<ScreenProductDao, ScreenProduct> {

	@Autowired
	private ScreenProductDao screenProductDao;
	@Autowired
	private AmFileUploadService amFileUploadService;
	/**
	 * 获取单条数据
	 * @param screenProduct
	 * @return
	 */
	@Override
	public ScreenProduct get(ScreenProduct screenProduct) {
		return super.get(screenProduct);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param screenProduct
	 * @return
	 */
	@Override
	public Page<ScreenProduct> findPage(Page<ScreenProduct> page, ScreenProduct screenProduct) {
		return super.findPage(page, screenProduct);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param screenProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ScreenProduct screenProduct) {
		super.save(screenProduct);
		// 保存上传图片
//		FileUploadUtils.saveFileUpload(screenProduct.getId(), "screenProduct_image");
	}
	
	/**
	 * 更新状态
	 * @param screenProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ScreenProduct screenProduct) {
		super.updateStatus(screenProduct);
	}
	
	/**
	 * 删除数据
	 * @param screenProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ScreenProduct screenProduct) {
		super.delete(screenProduct);
	}

	/**
	 * 根据企业编码获取企业的产品
	 * @return
	 */
	public List<ProductData> getProduct(List<String> enterpriseList) {
		return screenProductDao.getProduct(enterpriseList);
	}

	/**
	 * 获取企业下的产品
	 * @param enterpriseCode
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<ProductData> getProductList(String enterpriseCode, int pageNo, int pageSize) {
		if (pageNo == 1) {
			pageNo = 0;
		} else {
			pageNo = pageSize * (pageNo-1);
		}
		// 企业下的产品条数
		int count = screenProductDao.selectCount(enterpriseCode);
		List<ProductData> productDataList = screenProductDao.getProductList(enterpriseCode, pageNo, pageSize);
		if (ListUtils.isNotEmpty(productDataList)) {
			productImg(productDataList);
		}
		Page<ProductData> page = new Page<>();
		page.setList(productDataList);
		page.setCount(count);
		return page;
	}

	/**
	 * 根据产品编码获取图片
	 * @param productDataList
	 */
	@Transactional(readOnly = true)
	public void productImg(List<ProductData> productDataList) {
		List<String> productList = ListUtils.newArrayList();
		if (ListUtils.isNotEmpty(productDataList)) {
			productList = ListUtils.extractToList(productDataList, "productCode");
		}
		if (ListUtils.isNotEmpty(productDataList)) {
			List<AmFileUpload> imgList = amFileUploadService.getImgs(productList, "screenProduct_image");
			for (ProductData productData : productDataList) {
				Optional<AmFileUpload> optionalCoverData = imgList.stream().filter(s ->s.getBizKey().equals(productData.getProductCode())).findFirst();
				if (optionalCoverData.isPresent()) {
					productData.setProductImg(optionalCoverData.get().getFileUrl());
				}
			}
		}
	}
}