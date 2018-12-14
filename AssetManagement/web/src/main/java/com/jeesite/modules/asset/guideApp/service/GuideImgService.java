/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.guideApp.entity.GuideImg;
import com.jeesite.modules.asset.guideApp.dao.GuideImgDao;

/**
 * 活动管理图片Service
 * @author len
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly=true)
public class GuideImgService extends CrudService<GuideImgDao, GuideImg> {
	
	/**
	 * 获取单条数据
	 * @param guideImg
	 * @return
	 */
	@Override
	public GuideImg get(GuideImg guideImg) {
		return super.get(guideImg);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param guideImg
	 * @return
	 */
	@Override
	public Page<GuideImg> findPage(Page<GuideImg> page, GuideImg guideImg) {
		return super.findPage(page, guideImg);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param guideImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(GuideImg guideImg) {
		super.save(guideImg);
	}
	
	/**
	 * 更新状态
	 * @param guideImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(GuideImg guideImg) {
		super.updateStatus(guideImg);
	}
	
	/**
	 * 删除数据
	 * @param guideImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(GuideImg guideImg) {
		super.delete(guideImg);
	}
	
}