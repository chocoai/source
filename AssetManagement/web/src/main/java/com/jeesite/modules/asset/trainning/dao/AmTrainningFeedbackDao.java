/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.trainning.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.trainning.entity.AmTrainningFeedback;

/**
 * 培训反馈DAO接口
 * @author scarlett
 * @version 2018-06-11
 */
@MyBatisDao
public interface AmTrainningFeedbackDao extends CrudDao<AmTrainningFeedback> {
	int findHasSubmitted(String trainningCode,String writtenBy);
	int deleteDb(String pkey);
}