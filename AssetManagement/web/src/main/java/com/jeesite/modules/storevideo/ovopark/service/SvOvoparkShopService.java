/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkShop;
import com.jeesite.modules.storevideo.ovopark.dao.SvOvoparkShopDao;

/**
 * 万店掌门店Service
 * @author Philip Guan
 * @version 2019-02-19
 */
@Service
@Transactional(readOnly=true)
public class SvOvoparkShopService extends CrudService<SvOvoparkShopDao, SvOvoparkShop> {
	
	/**
	 * 获取单条数据
	 * @param svOvoparkShop
	 * @return
	 */
	@Override
	public SvOvoparkShop get(SvOvoparkShop svOvoparkShop) {
		return super.get(svOvoparkShop);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svOvoparkShop
	 * @return
	 */
	@Override
	public Page<SvOvoparkShop> findPage(Page<SvOvoparkShop> page, SvOvoparkShop svOvoparkShop) {
		return super.findPage(page, svOvoparkShop);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svOvoparkShop
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvOvoparkShop svOvoparkShop) {
		super.save(svOvoparkShop);
	}
	
	/**
	 * 更新状态
	 * @param svOvoparkShop
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvOvoparkShop svOvoparkShop) {
		super.updateStatus(svOvoparkShop);
	}
	
	/**
	 * 删除数据
	 * @param svOvoparkShop
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvOvoparkShop svOvoparkShop) {
		super.delete(svOvoparkShop);
	}
	
}