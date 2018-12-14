/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.TbItemImgsDao;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * tb_item_imgsService
 * @author jace
 * @version 2018-07-08
 */
@Service
@Transactional(readOnly=true)
public class TbItemImgsService extends CrudService<TbItemImgsDao, TbItemImgs> {
	
	/**
	 * 获取单条数据
	 * @param tbItemImgs
	 * @return
	 */
	@Override
	public TbItemImgs get(TbItemImgs tbItemImgs) {
		return super.get(tbItemImgs);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbItemImgs
	 * @return
	 */
	@Override
	public Page<TbItemImgs> findPage(Page<TbItemImgs> page, TbItemImgs tbItemImgs) {
		return super.findPage(page, tbItemImgs);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbItemImgs
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbItemImgs tbItemImgs) {
		super.save(tbItemImgs);
	}
	
	/**
	 * 更新状态
	 * @param tbItemImgs
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbItemImgs tbItemImgs) {
		super.updateStatus(tbItemImgs);
	}
	
	/**
	 * 删除数据
	 * @param tbItemImgs
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbItemImgs tbItemImgs) {
		super.delete(tbItemImgs);
	}
	
}