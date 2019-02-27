/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.camera.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.camera.entity.SvCamera;

/**
 * 摄像头DAO接口
 * @author AlbertFeng
 * @version 2019-01-17
 */
@MyBatisDao
public interface SvCameraDao extends CrudDao<SvCamera> {
	
}