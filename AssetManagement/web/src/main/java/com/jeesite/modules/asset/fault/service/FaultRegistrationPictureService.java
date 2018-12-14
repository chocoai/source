/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.service;

import java.util.List;

import com.jeesite.modules.asset.fault.dao.FaultRegistrationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.fault.entity.FaultRegistrationPicture;
import com.jeesite.modules.asset.fault.dao.FaultRegistrationPictureDao;

/**
 * 故障登记图片Service
 * @author Scarlett
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly=true)
public class FaultRegistrationPictureService extends CrudService<FaultRegistrationPictureDao, FaultRegistrationPicture> {
	@Autowired
	private FaultRegistrationPictureDao dao;
	
	/**
	 * 获取单条数据
	 * @param faultRegistrationPicture
	 * @return
	 */
	@Override
	public FaultRegistrationPicture get(FaultRegistrationPicture faultRegistrationPicture) {
		return super.get(faultRegistrationPicture);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param faultRegistrationPicture
	 * @return
	 */
	@Override
	public Page<FaultRegistrationPicture> findPage(Page<FaultRegistrationPicture> page, FaultRegistrationPicture faultRegistrationPicture) {
		return super.findPage(page, faultRegistrationPicture);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param faultRegistrationPicture
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FaultRegistrationPicture faultRegistrationPicture) {
		super.save(faultRegistrationPicture);
	}
	
	/**
	 * 更新状态
	 * @param faultRegistrationPicture
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FaultRegistrationPicture faultRegistrationPicture) {
		super.updateStatus(faultRegistrationPicture);
	}
	
	/**
	 * 删除数据
	 */
	@Transactional(readOnly=false)
	public void delete(String faultpicCode) {
		dao.deleteData(faultpicCode);
	}
	@Transactional(readOnly = true)
	public List<FaultRegistrationPicture> findPicsByRegistrationCode(String registrationCode){
		return dao.findPicsByRegistrationCode(registrationCode);
	}
	/**
	 *绑定故障单号到图片上
	 */
	@Transactional(readOnly=false)
	public void updateRegistrationCode(String registrationCode,String faultpicCode){
		dao.updateRegistrationCode(registrationCode,faultpicCode);
	}
}