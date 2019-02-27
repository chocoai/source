/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.video.entity.SvFaceDetectLog;

/**
 * 人脸识别记录DAO接口
 * @author Philip Guan
 * @version 2019-01-18
 */
@MyBatisDao
public interface SvFaceDetectLogDao extends CrudDao<SvFaceDetectLog> {
	
}