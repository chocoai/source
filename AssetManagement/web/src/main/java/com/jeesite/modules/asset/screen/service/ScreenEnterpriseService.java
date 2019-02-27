/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.screen.entity.ScreenEnterprise;
import com.jeesite.modules.asset.screen.dao.ScreenEnterpriseDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 零售家企业Service
 * @author len
 * @version 2019-01-08
 */
@Service
@Transactional(readOnly=true)
public class ScreenEnterpriseService extends CrudService<ScreenEnterpriseDao, ScreenEnterprise> {
	
	/**
	 * 获取单条数据
	 * @param screenEnterprise
	 * @return
	 */
	@Override
	public ScreenEnterprise get(ScreenEnterprise screenEnterprise) {
		return super.get(screenEnterprise);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param screenEnterprise
	 * @return
	 */
	@Override
	public Page<ScreenEnterprise> findPage(Page<ScreenEnterprise> page, ScreenEnterprise screenEnterprise) {
		return super.findPage(page, screenEnterprise);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param screenEnterprise
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ScreenEnterprise screenEnterprise) {
		super.save(screenEnterprise);
		// 保存上传图片
//		FileUploadUtils.saveFileUpload(screenEnterprise.getId(), "screenEnterprise_image");
	}
	
	/**
	 * 更新状态
	 * @param screenEnterprise
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ScreenEnterprise screenEnterprise) {
		super.updateStatus(screenEnterprise);
	}
	
	/**
	 * 删除数据
	 * @param screenEnterprise
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ScreenEnterprise screenEnterprise) {
		super.delete(screenEnterprise);
	}
	
}