/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.softcostume.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.softcostume.entity.SvSoftRlat;
import com.jeesite.modules.storevideo.softcostume.dao.SvSoftRlatDao;

/**
 * 店铺视频关系Service
 * @author len
 * @version 2019-02-26
 */
@Service
@Transactional(readOnly=true)
public class SvSoftRlatService extends CrudService<SvSoftRlatDao, SvSoftRlat> {
	@Autowired
	private SvSoftRlatDao svSoftRlatDao;
	/**
	 * 获取单条数据
	 * @param svSoftRlat
	 * @return
	 */
	@Override
	public SvSoftRlat get(SvSoftRlat svSoftRlat) {
		return super.get(svSoftRlat);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svSoftRlat
	 * @return
	 */
	@Override
	public Page<SvSoftRlat> findPage(Page<SvSoftRlat> page, SvSoftRlat svSoftRlat) {
		return super.findPage(page, svSoftRlat);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svSoftRlat
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvSoftRlat svSoftRlat) {
		super.save(svSoftRlat);
	}
	
	/**
	 * 更新状态
	 * @param svSoftRlat
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvSoftRlat svSoftRlat) {
		super.updateStatus(svSoftRlat);
	}
	
	/**
	 * 删除数据
	 * @param svSoftRlat
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvSoftRlat svSoftRlat) {
		super.delete(svSoftRlat);
	}


	@Transactional(readOnly=false)
	public void deleteAndInsert(String videoId, List<SvSoftRlat> list) {
		svSoftRlatDao.deleteByVideoId(videoId);
		svSoftRlatDao.insertBatch(list);
	}

}