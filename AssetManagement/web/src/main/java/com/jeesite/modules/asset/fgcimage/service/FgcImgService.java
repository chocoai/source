/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcimage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.fgcimage.entity.FgcImg;
import com.jeesite.modules.asset.fgcimage.dao.FgcImgDao;

/**
 * 梵工厂图片表Service
 * @author len
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly=true)
public class FgcImgService extends CrudService<FgcImgDao, FgcImg> {
	@Autowired
	private FgcImgDao fgcImgDao;
	/**
	 * 获取单条数据
	 * @param fgcImg
	 * @return
	 */
	@Override
	public FgcImg get(FgcImg fgcImg) {
		return super.get(fgcImg);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fgcImg
	 * @return
	 */
	@Override
	public Page<FgcImg> findPage(Page<FgcImg> page, FgcImg fgcImg) {
		return super.findPage(page, fgcImg);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fgcImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FgcImg fgcImg) {
		super.save(fgcImg);
	}
	
	/**
	 * 更新状态
	 * @param fgcImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FgcImg fgcImg) {
		super.updateStatus(fgcImg);
	}
	
	/**
	 * 删除数据
	 * @param fgcImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FgcImg fgcImg) {
		super.delete(fgcImg);
	}

	@Transactional(readOnly = false)
	public void saveData(List<FgcImg> fgcImgList) {
		fgcImgDao.insertBatch(fgcImgList);
	}
}