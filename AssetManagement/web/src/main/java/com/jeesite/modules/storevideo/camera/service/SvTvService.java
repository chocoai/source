/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.camera.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.camera.dao.SvTvDao;
import com.jeesite.modules.storevideo.camera.entity.SvTv;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电视在线客户端Service
 * @author Philip Guan
 * @version 2019-02-07
 */
@Service
@Transactional(readOnly=true)
public class SvTvService extends CrudService<SvTvDao, SvTv> {
	
	/**
	 * 获取单条数据
	 * @param svTv
	 * @return
	 */
	@Override
	public SvTv get(SvTv svTv) {
		return super.get(svTv);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svTv
	 * @return
	 */
	@Override
	public Page<SvTv> findPage(Page<SvTv> page, SvTv svTv) {
		return super.findPage(page, svTv);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svTv
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvTv svTv) {
		super.save(svTv);
	}
	
	/**
	 * 更新状态
	 * @param svTv
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvTv svTv) {
		super.updateStatus(svTv);
	}
	
	/**
	 * 删除数据
	 * @param svTv
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvTv svTv) {
		super.delete(svTv);
	}
	
}