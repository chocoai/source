/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.service;

import java.util.List;

import com.jeesite.modules.storevideo.video.dao.SvVideoDao;
import com.jeesite.modules.storevideo.video.entity.SvVideo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;

/**
 * 店铺视频Service
 * @author Philip Guan
 * @version 2019-01-16
 */
@Service
@Transactional(readOnly=true)
public class SvVideoService extends CrudService<SvVideoDao, SvVideo> {
	
	/**
	 * 获取单条数据
	 * @param svVideo
	 * @return
	 */
	@Override
	public SvVideo get(SvVideo svVideo) {
		return super.get(svVideo);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svVideo
	 * @return
	 */
	@Override
	public Page<SvVideo> findPage(Page<SvVideo> page, SvVideo svVideo) {
		return super.findPage(page, svVideo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svVideo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvVideo svVideo) {
		super.save(svVideo);
	}
	
	/**
	 * 更新状态
	 * @param svVideo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvVideo svVideo) {
		super.updateStatus(svVideo);
	}
	
	/**
	 * 删除数据
	 * @param svVideo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvVideo svVideo) {
		super.delete(svVideo);
	}
	
}