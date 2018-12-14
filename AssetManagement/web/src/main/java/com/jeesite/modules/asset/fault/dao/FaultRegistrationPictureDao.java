/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.fault.entity.FaultRegistrationPicture;

import java.util.List;

/**
 * 故障登记图片DAO接口
 * @author Scarlett
 * @version 2018-07-14
 */
@MyBatisDao
public interface FaultRegistrationPictureDao extends CrudDao<FaultRegistrationPicture> {
	List<FaultRegistrationPicture> findPicsByRegistrationCode(String registrationCode);
	void deleteData(String faultpicCode);
	void updateRegistrationCode(String registrationCode, String faultpicCode);
}