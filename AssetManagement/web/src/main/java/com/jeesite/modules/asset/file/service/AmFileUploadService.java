/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.file.service;

import java.util.List;
import java.util.Map;

import com.jeesite.modules.asset.util.ParamentUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.dao.AmFileUploadDao;

/**
 * 文件上传表Service
 * @author len
 * @version 2018-08-10
 */
@Service
@Transactional(readOnly=true)
public class AmFileUploadService extends CrudService<AmFileUploadDao, AmFileUpload> {
	@Autowired
	private AmFileUploadDao amFileUploadDao;
	/**
	 * 获取单条数据
	 * @param amFileUpload
	 * @return
	 */
	@Override
	public AmFileUpload get(AmFileUpload amFileUpload) {
		return super.get(amFileUpload);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amFileUpload
	 * @return
	 */
	@Override
	public Page<AmFileUpload> findPage(Page<AmFileUpload> page, AmFileUpload amFileUpload) {
		return super.findPage(page, amFileUpload);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amFileUpload
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmFileUpload amFileUpload) {
		super.save(amFileUpload);
	}
	
	/**
	 * 更新状态
	 * @param amFileUpload
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmFileUpload amFileUpload) {
		super.updateStatus(amFileUpload);
	}
	
	/**
	 * 删除数据
	 * @param amFileUpload
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmFileUpload amFileUpload) {
		super.delete(amFileUpload);
	}

	/**
	 * 根据bizkey和bizType获取信息
	 * @param bizKey
	 * @param bizType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<AmFileUpload> getImg(String bizKey, String bizType) {
		return amFileUploadDao.getImg(bizKey, bizType);
	}

	/**
	 * 保存或删除图片数据
	 * @param fileUploadList
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void saveData (List<AmFileUpload> fileUploadList, List<String> ids) {
		// 保存图片信息到数据库
		if (ParamentUntil.isBackList(fileUploadList)) {
			amFileUploadDao.insertBatch(fileUploadList);
		}
		// 根据id删除图片信息
		if (ParamentUntil.isBackList(ids)) {
			for (String id : ids) {
				amFileUploadDao.deleteDb(id);
			}
		}
	}
	/**
	 * 保存图片审核单备注和审核是否通过
	 */
	@Transactional(readOnly = false)
	public int updatePicRemark(String id,String picStatus,String picRemarks){
		return  amFileUploadDao.updatePicRemark(id,picStatus,picRemarks);
	}

	public List<AmFileUpload> getImage(List<String> appreciation) {
		return amFileUploadDao.getImage(appreciation);
	}

	/**
	 * 根据bizkey 和biztype获取图片
	 * @param bizKeyList
	 * @param bizType
	 * @return
	 */
	public List<AmFileUpload> getImgs(List<String> bizKeyList, String bizType) {
		return amFileUploadDao.getImgs(bizKeyList, bizType);
	}
}