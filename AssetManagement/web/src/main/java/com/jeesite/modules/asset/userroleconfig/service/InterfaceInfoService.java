///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.service;
//
//import com.jeesite.common.entity.Page;
//import com.jeesite.common.service.CrudService;
//import com.jeesite.modules.asset.userroleconfig.dao.InterfaceFieldDao;
//import com.jeesite.modules.asset.userroleconfig.dao.InterfaceInfoDao;
//import com.jeesite.modules.asset.userroleconfig.dao.InterfaceQueryDao;
//import com.jeesite.modules.asset.userroleconfig.dao.UserDataPermissionDao;
//import com.jeesite.modules.asset.userroleconfig.entity.InterfaceField;
//import com.jeesite.modules.asset.userroleconfig.entity.InterfaceInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * 接口信息Service
// * @author dwh
// * @version 2018-07-17
// */
//@Service
//@Transactional(readOnly=true)
//public class InterfaceInfoService extends CrudService<InterfaceInfoDao, InterfaceInfo> {
//	@Autowired
//	private InterfaceInfoDao interfaceInfoDao;
//
//	@Autowired
//	private InterfaceFieldDao interfaceFieldDao;
//	@Autowired
//	private UserDataPermissionDao userDataPermissionDao;
//	@Autowired
//	private InterfaceQueryDao interfaceQueryDao;
//
//	/**
//	 * 获取单条数据
//	 * @param interfaceInfo
//	 * @return
//	 */
//	@Override
//	public InterfaceInfo get(InterfaceInfo interfaceInfo) {
//		InterfaceInfo entity = super.get(interfaceInfo);
//		if (entity != null){
//			InterfaceField interfaceField = new InterfaceField(entity);
//			interfaceField.setStatus(InterfaceField.STATUS_NORMAL);
//			entity.setInterfaceFieldList(interfaceFieldDao.findList(interfaceField));
//		}
//		return entity;
//	}
//
//	/**
//	 * 查询分页数据
//	 * @param page 分页对象
//	 * @param interfaceInfo
//	 * @return
//	 */
//	@Override
//	public Page<InterfaceInfo> findPage(Page<InterfaceInfo> page, InterfaceInfo interfaceInfo) {
//		return super.findPage(page, interfaceInfo);
//	}
//
//	/**
//	 * 保存数据（插入或更新）
//	 * @param interfaceInfo
//	 */
//	@Override
//	@Transactional(readOnly=false)
//	public void save(InterfaceInfo interfaceInfo) {
//		super.save(interfaceInfo);
//		// 保存 InterfaceInfo子表
//		for (InterfaceField interfaceField : interfaceInfo.getInterfaceFieldList()){
//			if (!InterfaceField.STATUS_DELETE.equals(interfaceField.getStatus())){
//				interfaceField.setInterfaceCode(interfaceInfo);
//				if (interfaceField.getIsNewRecord()){
//					interfaceField.preInsert();
//					interfaceFieldDao.insert(interfaceField);
//				}else{
//					interfaceField.preUpdate();
//					interfaceFieldDao.update(interfaceField);
//				}
//			}else{
//				interfaceFieldDao.delete(interfaceField);
//			}
//		}
//	}
//
//	/**
//	 * 更新状态
//	 * @param interfaceInfo
//	 */
//	@Override
//	@Transactional(readOnly=false)
//	public void updateStatus(InterfaceInfo interfaceInfo) {
//		super.updateStatus(interfaceInfo);
//	}
//
//	/**
//	 * 删除数据
//	 * @param interfaceInfo
//	 */
//	@Override
//	@Transactional(readOnly=false)
//	public void delete(InterfaceInfo interfaceInfo) {
//		super.delete(interfaceInfo);
//		InterfaceField interfaceField = new InterfaceField();
//		interfaceField.setInterfaceCode(interfaceInfo);
//		interfaceFieldDao.delete(interfaceField);
//	}
////	@Transactional(readOnly=false)
//	public List<InterfaceInfo> listDataByUserCode(String userCode) {
//		List<InterfaceInfo> interfaceInfoList=interfaceInfoDao.listDataByUserCode(userCode);
//		return  interfaceInfoList;
//	}
//	@Transactional(readOnly=false)
//	public void deluserInfterface(String userCode, String interFaceCode) {
//		interfaceInfoDao.deluserInfterface(userCode,interFaceCode);
//		interfaceQueryDao.deluserInfterfaceQuery(userCode,interFaceCode);
//	}
//
//	/**
//	 * 查询用户没有的接口的数据
//	 * @return
//	 */
//	public List<InterfaceInfo> getInfoByNotInUser(String userCode) {
//	List<InterfaceInfo> interfaceInfos=interfaceInfoDao.getInfoByNotInUser(userCode);
//	return interfaceInfos;
//	}
//
//	@Transactional(readOnly=false)
//	public void deleteDb(String interFaceCode) {
//		interfaceInfoDao.deleteDb(interFaceCode);
//		interfaceFieldDao.deleteDb(interFaceCode);
//		interfaceQueryDao.deleteDbByInterFaceCode(interFaceCode);
//		userDataPermissionDao.deleteDbByInterFaceCode(interFaceCode);
//	}
//
//	public List<InterfaceInfo> getInfoByUserCodeAndIntCode(String userCode, String interfaceCode) {
//		List<InterfaceInfo> interfaceInfos=interfaceInfoDao.getInfoByUserCodeAndIntCode(userCode,interfaceCode);
//		return interfaceInfos;
//	}
//
//	public List<InterfaceField> getFieldByInterCode(String interfaceCode) {
//		InterfaceInfo interfaceInfo=new InterfaceInfo();
//		interfaceInfo.setInterfaceCode(interfaceCode);
//		InterfaceField interfaceField=new InterfaceField();
//		interfaceField.setInterfaceCode(interfaceInfo);
//		List<InterfaceField> fields=interfaceFieldDao.findList(interfaceField);
//		return fields;
//	}
//
//	//获取参数信息根据参数编码
//	public InterfaceField getInterFaceFieldByCode(String fieldCode) {
//		InterfaceField interfaceField=new InterfaceField();
//		interfaceField.setFieldCode(fieldCode);
//		return interfaceFieldDao.get(interfaceField);
//	}
//}