///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.service;
//
//import com.jeesite.common.entity.Page;
//import com.jeesite.common.service.CrudService;
//import com.jeesite.modules.asset.userroleconfig.dao.UserDataPermissionDao;
//import com.jeesite.modules.asset.userroleconfig.entity.UserDataPermission;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * 用户数据权限Service
// * @author dwh
// * @version 2018-07-18
// */
//@Service
//@Transactional(readOnly=true)
//public class UserDataPermissionService extends CrudService<UserDataPermissionDao, UserDataPermission> {
//
//	@Autowired
//	private UserDataPermissionDao userDataPermissionDao;
//	/**
//	 * 获取单条数据
//	 * @param userDataPermission
//	 * @return
//	 */
//	@Override
//	public UserDataPermission get(UserDataPermission userDataPermission) {
//		return super.get(userDataPermission);
//	}
//
//	/**
//	 * 查询分页数据
//	 * @param page 分页对象
//	 * @param userDataPermission
//	 * @return
//	 */
//	@Override
//	public Page<UserDataPermission> findPage(Page<UserDataPermission> page, UserDataPermission userDataPermission) {
//		return super.findPage(page, userDataPermission);
//	}
//
//	/**
//	 * 保存数据（插入或更新）
//	 * @param userDataPermission
//	 */
//	@Override
//	@Transactional(readOnly=false)
//	public void save(UserDataPermission userDataPermission) {
//		super.save(userDataPermission);
//	}
//
//	/**
//	 * 更新状态
//	 * @param userDataPermission
//	 */
//	@Override
//	@Transactional(readOnly=false)
//	public void updateStatus(UserDataPermission userDataPermission) {
//		super.updateStatus(userDataPermission);
//	}
//
//	/**
//	 * 删除数据
//	 * @param userDataPermission
//	 */
//	@Override
//	@Transactional(readOnly=false)
//	public void delete(UserDataPermission userDataPermission) {
//		super.delete(userDataPermission);
//	}
//
//	public List<UserDataPermission> getListDataByUserCode(String userCode) {
//		List<UserDataPermission> userDataPermission=userDataPermissionDao.getListByUserCode(userCode);
//		return  userDataPermission;
//	}
//
//}