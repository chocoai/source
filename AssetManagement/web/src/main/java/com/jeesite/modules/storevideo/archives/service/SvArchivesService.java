/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.archives.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.archives.entity.SvArchives;
import com.jeesite.modules.storevideo.archives.dao.SvArchivesDao;

/**
 * 产品推送基础档案Service
 * @author len
 * @version 2019-02-26
 */
@Service
@Transactional(readOnly=true)
public class SvArchivesService extends CrudService<SvArchivesDao, SvArchives> {
	
	/**
	 * 获取单条数据
	 * @param svArchives
	 * @return
	 */
	@Override
	public SvArchives get(SvArchives svArchives) {
		return super.get(svArchives);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svArchives
	 * @return
	 */
	@Override
	public Page<SvArchives> findPage(Page<SvArchives> page, SvArchives svArchives) {
		return super.findPage(page, svArchives);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svArchives
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvArchives svArchives) {
		super.save(svArchives);
	}
	
	/**
	 * 更新状态
	 * @param svArchives
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvArchives svArchives) {
		super.updateStatus(svArchives);
	}
	
	/**
	 * 删除数据
	 * @param svArchives
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvArchives svArchives) {
		super.delete(svArchives);
	}
	
}