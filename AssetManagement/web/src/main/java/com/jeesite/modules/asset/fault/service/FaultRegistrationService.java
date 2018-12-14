/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.jeesite.modules.asset.fault.entity.FaultRegistrationPicture;
import com.jeesite.modules.asset.util.service.AmUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.fault.entity.FaultRegistration;
import com.jeesite.modules.asset.fault.dao.FaultRegistrationDao;

/**
 * 故障登记单Service
 * @author Scarlett
 * @version 2018-07-11
 */
@Service
@Transactional(readOnly=true)
public class FaultRegistrationService extends CrudService<FaultRegistrationDao, FaultRegistration> {
	@Autowired
	private FaultRegistrationDao dao;
	@Autowired
	private FaultRegistrationPictureService pictureService;
	@Autowired
	private AmUtilService amUtilService;
	@Value("${ENDPOINT}")
	private String ENDPOINT;
	/**
	 * 获取单条数据
	 * @param faultRegistration
	 * @return
	 */
	@Override
	public FaultRegistration get(FaultRegistration faultRegistration) {
		return super.get(faultRegistration);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param faultRegistration
	 * @return
	 */
	@Override
	public Page<FaultRegistration> findPage(Page<FaultRegistration> page, FaultRegistration faultRegistration) {
		return super.findPage(page, faultRegistration);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param faultRegistration
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FaultRegistration faultRegistration) {
		super.save(faultRegistration);
	}

	/**
	 * 更新状态
	 * @param faultRegistration
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FaultRegistration faultRegistration) {
		super.updateStatus(faultRegistration);
	}

	/**
	 * 删除数据
	 *
	 */

	@Transactional(readOnly=false)
	public void delete(String registrationCode ) {
		dao.deleteDataFromDb(registrationCode);
	}
	@Transactional(readOnly=false)
	public boolean deleteData(String registrationCode) {
		boolean flag=true;
		String[] str = registrationCode.split(",");
		List<String> keys=new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			List<FaultRegistrationPicture> picList=pictureService.findPicsByRegistrationCode(str[i]);
			if(picList!=null &&picList.size()>0){
				for(int j=0;j<picList.size();j++) {
					FaultRegistrationPicture pic = picList.get(j);
					String path=this.getAliPath(pic.getSavePath());
					if(null!=path &&!"".equals(path)) {
						keys.add(path);
					}
					pictureService.delete(pic.getFaultpicCode());
				}
			}
			this.delete(str[i]);
		}
		/*if(null!=keys &&keys.size()>0) {
			this.deletePicAli(keys);
		}*/
		amUtilService.deletePicAli(keys);
		return true;
	}


	/**
	 * 从服务器删除图片
	 * @param keys
	 *//*
	public void deletePicAli(List<String> keys){
		OSSClient ossClient = new OSSClient("https://" + ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		// 执行删除阿里云文件
		ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(keys));
	}*/

	/**
	 * 截取删除图片的key值
	 * @param path
	 * @return
	 */
	public String getAliPath(String path){
		String path1=ENDPOINT;
		path=path.substring(path.indexOf(path1)+path1.length());
		return path;
	}
}