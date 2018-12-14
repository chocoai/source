/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.service;

import java.util.List;

import com.jeesite.modules.asset.fault.entity.BugRegistration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.fault.entity.BugRegistrationPicture;
import com.jeesite.modules.asset.fault.dao.BugRegistrationPictureDao;

/**
 * 线上bug登记图片Service
 * @author scarlett
 * @version 2018-10-25
 */
@Service
@Transactional(readOnly=true)
public class BugRegistrationPictureService extends CrudService<BugRegistrationPictureDao, BugRegistrationPicture> {
	
	/**
	 * 获取单条数据
	 * @param bugRegistrationPicture
	 * @return
	 */
	@Override
	public BugRegistrationPicture get(BugRegistrationPicture bugRegistrationPicture) {
		return super.get(bugRegistrationPicture);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param bugRegistrationPicture
	 * @return
	 */
	@Override
	public Page<BugRegistrationPicture> findPage(Page<BugRegistrationPicture> page, BugRegistrationPicture bugRegistrationPicture) {
		return super.findPage(page, bugRegistrationPicture);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bugRegistrationPicture
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BugRegistrationPicture bugRegistrationPicture) {
		super.save(bugRegistrationPicture);
	}
	
	/**
	 * 更新状态
	 * @param bugRegistrationPicture
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BugRegistrationPicture bugRegistrationPicture) {
		super.updateStatus(bugRegistrationPicture);
	}
	
	/**
	 * 删除数据
	 * @param bugRegistrationPicture
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BugRegistrationPicture bugRegistrationPicture) {
		super.delete(bugRegistrationPicture);
	}
	public List<BugRegistrationPicture> findPicsByBugCode(String bugCode){
		return dao.findPicsByBugCode(bugCode);
	}

	/**
	 * 关联故障单对应的图片
	 *
	 */
	@Transactional(readOnly=false)
	public void updateBugCode(String bugCode, String picCode) {
		dao.updateBugCode(bugCode,picCode);
	}
	@Transactional(readOnly=false)
	public void delete(String bugpicCode) {
		dao.deleteData(bugpicCode);
	}

	/*public List<BugRegistration> findPicsByBugCode(String s) {

	}*/
}