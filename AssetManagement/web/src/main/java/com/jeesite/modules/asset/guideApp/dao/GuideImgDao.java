/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.guideApp.entity.GuideImg;

/**
 * 活动管理图片DAO接口
 * @author len
 * @version 2018-12-12
 */
@MyBatisDao
public interface GuideImgDao extends CrudDao<GuideImg> {
	
}