/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.video.entity.SvVideo;

/**
 * 店铺视频DAO接口
 * @author Philip Guan
 * @version 2019-01-16
 */
@MyBatisDao
public interface SvVideoDao extends CrudDao<SvVideo> {
	
}