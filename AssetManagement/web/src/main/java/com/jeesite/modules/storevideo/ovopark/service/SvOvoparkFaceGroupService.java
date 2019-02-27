/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkFaceGroup;
import com.jeesite.modules.storevideo.ovopark.dao.SvOvoparkFaceGroupDao;

/**
 * 万店掌人脸分组Service
 * @author Philip Guan
 * @version 2019-02-18
 */
@Service
@Transactional(readOnly=true)
public class SvOvoparkFaceGroupService extends CrudService<SvOvoparkFaceGroupDao, SvOvoparkFaceGroup> {

	@Autowired
	SvOvoparkFaceGroupDao svOvoparkFaceGroupDao;

	/**
	 * 获取单条数据
	 * @param svOvoparkFaceGroup
	 * @return
	 */
	@Override
	public SvOvoparkFaceGroup get(SvOvoparkFaceGroup svOvoparkFaceGroup) {
		return super.get(svOvoparkFaceGroup);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svOvoparkFaceGroup
	 * @return
	 */
	@Override
	public Page<SvOvoparkFaceGroup> findPage(Page<SvOvoparkFaceGroup> page, SvOvoparkFaceGroup svOvoparkFaceGroup) {
		return super.findPage(page, svOvoparkFaceGroup);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svOvoparkFaceGroup
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvOvoparkFaceGroup svOvoparkFaceGroup) {
		super.save(svOvoparkFaceGroup);
	}
	
	/**
	 * 更新状态
	 * @param svOvoparkFaceGroup
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvOvoparkFaceGroup svOvoparkFaceGroup) {
		super.updateStatus(svOvoparkFaceGroup);
	}
	
	/**
	 * 删除数据
	 * @param svOvoparkFaceGroup
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvOvoparkFaceGroup svOvoparkFaceGroup) {
		super.delete(svOvoparkFaceGroup);
	}

	@Transactional(readOnly=false)
	public void deleteAll(){
		svOvoparkFaceGroupDao.deleteAll();
	}
}