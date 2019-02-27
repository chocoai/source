/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.video.entity.SvFaceDetectLog;
import com.jeesite.modules.storevideo.video.dao.SvFaceDetectLogDao;

/**
 * 人脸识别记录Service
 * @author Philip Guan
 * @version 2019-01-18
 */
@Service
@Transactional(readOnly=true)
public class SvFaceDetectLogService extends CrudService<SvFaceDetectLogDao, SvFaceDetectLog> {
	
	/**
	 * 获取单条数据
	 * @param svFaceDetectLog
	 * @return
	 */
	@Override
	public SvFaceDetectLog get(SvFaceDetectLog svFaceDetectLog) {
		return super.get(svFaceDetectLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svFaceDetectLog
	 * @return
	 */
	@Override
	public Page<SvFaceDetectLog> findPage(Page<SvFaceDetectLog> page, SvFaceDetectLog svFaceDetectLog) {
		return super.findPage(page, svFaceDetectLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svFaceDetectLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvFaceDetectLog svFaceDetectLog) {
		super.save(svFaceDetectLog);
	}
	
	/**
	 * 更新状态
	 * @param svFaceDetectLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvFaceDetectLog svFaceDetectLog) {
		super.updateStatus(svFaceDetectLog);
	}
	
	/**
	 * 删除数据
	 * @param svFaceDetectLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvFaceDetectLog svFaceDetectLog) {
		super.delete(svFaceDetectLog);
	}
	
}