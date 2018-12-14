/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jeesite.modules.asset.fault.entity.BugRegistrationPicture;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.service.AmUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.fault.entity.BugRegistration;
import com.jeesite.modules.asset.fault.dao.BugRegistrationDao;

/**
 * 线上bug登记单Service
 * @author Scarlett
 * @version 2018-10-25
 */
@Service
@Transactional(readOnly=true)
public class BugRegistrationService extends CrudService<BugRegistrationDao, BugRegistration> {
	@Autowired
	private BugRegistrationPictureService pictureService;
	@Autowired
	private FaultRegistrationService faultRegistrationService;
	@Autowired
	private AmUtilService amUtilService;


	/**
	 * 获取单条数据
	 * @param bugRegistration
	 * @return
	 */
	@Override
	public BugRegistration get(BugRegistration bugRegistration) {
		return super.get(bugRegistration);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param bugRegistration
	 * @return
	 */
	@Override
	public Page<BugRegistration> findPage(Page<BugRegistration> page, BugRegistration bugRegistration) {
		return super.findPage(page, bugRegistration);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bugRegistration
	 */

	@Transactional(readOnly=false)
	public void save(BugRegistration bugRegistration,String bugpicCodes) {
		super.save(bugRegistration);
		if (bugpicCodes != null && !"".equals(bugpicCodes)) {
			List<String> bugpicCodeList = Arrays.asList(bugpicCodes.split(","));
			String bugCode=bugRegistration.getBugCode();
			if(ParamentUntil.isBackList(bugpicCodeList)){
				for(String str:bugpicCodeList){
					pictureService.updateBugCode(bugCode,str);
				}
			}
		}

	}
	
	/**
	 * 更新状态
	 * @param bugRegistration
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BugRegistration bugRegistration) {
		super.updateStatus(bugRegistration);
	}
	
	/**
	 * 删除数据
	 *
	 */

	@Transactional(readOnly=false)
	public void delete(	String registrationCode) {
		dao.deleteDataFromDb(registrationCode);
	}
	@Transactional(readOnly=false)
    public Boolean deleteData(String bugRegistrationCode) {
		String[] str = bugRegistrationCode.split(",");
		List<String> keys=new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			List<BugRegistrationPicture> picList=pictureService.findPicsByBugCode(str[i]);
			if(picList!=null &&picList.size()>0){
				for(int j=0;j<picList.size();j++) {
					BugRegistrationPicture pic = picList.get(j);
					String path=faultRegistrationService.getAliPath(pic.getSavePath());
					if(null!=path &&!"".equals(path)) {
						keys.add(path);
					}
					pictureService.delete(pic.getBugpicCode());
				}
			}
			this.delete(str[i]);
		}
		amUtilService.deletePicAli(keys);
		return true;
    }


}