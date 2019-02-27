/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.TbProductDao;
import com.jeesite.modules.asset.tianmao.dao.TbSkuDao;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.distribution.pricelog.dao.DistrPriceLogDao;
import com.jeesite.modules.distribution.pricelog.entity.DistrPriceLog;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private TbSkuDao tbSkuDao;
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

	@Autowired
	private DistrPriceLogDao distrPriceLogDao;
	/**
	 * 保存数据（插入或更新）
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProduct tbProduct) {
		super.save(tbProduct);
		List<TbSku> tbSkuList = ListUtils.newArrayList();
		List<DistrPriceLog> logList = ListUtils.newArrayList();
		for (TbSku tbSku : tbProduct.getTbSkuList()) {
			if (new BigDecimal(0).compareTo(new BigDecimal(tbSku.getDistributionPrice())) != 0) {
				tbSkuList.add(tbSku);
			}
			// 如果原分销价和页面上更新后的分销价不一样 添加日志记录
			if (new BigDecimal(tbSku.getDistributionPrice()).compareTo(new BigDecimal(tbSku.getHidePrice())) != 0) {
				DistrPriceLog distrPriceLog = new DistrPriceLog();
				// 商品id
				distrPriceLog.setNumIid(tbProduct.getNumIid());
				// 商品名称
				distrPriceLog.setGoodsName(tbProduct.getTitle());
				// 原分销价
				distrPriceLog.setOriginalPrice(tbSku.getHidePrice());
				// 现价
				distrPriceLog.setCurrentPrice(tbSku.getDistributionPrice());
				// sku
				distrPriceLog.setSku(tbSku.getOuterId());
				// skuid
				distrPriceLog.setSkuId(String.valueOf(tbSku.getSkuId()));
				// 更新时间
				distrPriceLog.setTime(new Date());
				// 更新人
				distrPriceLog.setUpdateBy(UserUtils.getUser().getUserName());
				distrPriceLog.setIsNewRecord(true);
				logList.add(distrPriceLog);
			}
		}
		// 根据skuId更新分销价
		if (ListUtils.isNotEmpty(tbSkuList)) {
			tbSkuDao.updateDistributionPrice(tbSkuList);
		}
		// 添加日志
		if (ListUtils.isNotEmpty(logList)) {
			distrPriceLogDao.insertBatch(logList);
		}
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