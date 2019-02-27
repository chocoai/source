/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.member.entity.CrmMemberInfo;
import com.jeesite.modules.storevideo.member.dao.CrmMemberInfoDao;

/**
 * 淘宝会员信息Service
 * @author Albert Feng
 * @version 2019-02-16
 */
@Service
@Transactional(readOnly=true)
public class CrmMemberInfoService extends CrudService<CrmMemberInfoDao, CrmMemberInfo> {

	@Autowired
	private CrmMemberInfoDao crmMemberInfoDao;
	
	/**
	 * 获取单条数据
	 * @param crmMemberInfo
	 * @return
	 */
	@Override
	public CrmMemberInfo get(CrmMemberInfo crmMemberInfo) {
		return super.get(crmMemberInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param crmMemberInfo
	 * @return
	 */
	@Override
	public Page<CrmMemberInfo> findPage(Page<CrmMemberInfo> page, CrmMemberInfo crmMemberInfo) {
		return super.findPage(page, crmMemberInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param crmMemberInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CrmMemberInfo crmMemberInfo) {
		super.save(crmMemberInfo);
	}
	
	/**
	 * 更新状态
	 * @param crmMemberInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CrmMemberInfo crmMemberInfo) {
		super.updateStatus(crmMemberInfo);
	}
	
	/**
	 * 删除数据
	 * @param crmMemberInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CrmMemberInfo crmMemberInfo) {
		super.delete(crmMemberInfo);
	}

	/**
	 * 获取单条数据By手机号码
	 * @param mobile
	 * @return
	 */
	public CrmMemberInfo getMemberInfoByMobile(String mobile) {
		return crmMemberInfoDao.getMemberInfoByMobile(mobile);
	}
	
}