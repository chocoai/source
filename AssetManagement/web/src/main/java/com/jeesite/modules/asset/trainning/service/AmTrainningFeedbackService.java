/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.trainning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.trainning.entity.AmTrainningFeedback;
import com.jeesite.modules.asset.trainning.dao.AmTrainningFeedbackDao;

/**
 * 培训反馈Service
 * @author scarlett
 * @version 2018-06-11
 */
@Service
@Transactional(readOnly=true)
public class AmTrainningFeedbackService extends CrudService<AmTrainningFeedbackDao, AmTrainningFeedback> {
	@Autowired
	private AmTrainningFeedbackDao feedbackDao;
	/**
	 * 获取单条数据
	 * @param amTrainningFeedback
	 * @return
	 */
	@Override
	public AmTrainningFeedback get(AmTrainningFeedback amTrainningFeedback) {
		return super.get(amTrainningFeedback);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amTrainningFeedback
	 * @return
	 */
	@Override
	public Page<AmTrainningFeedback> findPage(Page<AmTrainningFeedback> page, AmTrainningFeedback amTrainningFeedback) {
		return super.findPage(page, amTrainningFeedback);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amTrainningFeedback
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmTrainningFeedback amTrainningFeedback) {
		super.save(amTrainningFeedback);
	}
	
	/**
	 * 更新状态
	 * @param amTrainningFeedback
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmTrainningFeedback amTrainningFeedback) {
		super.updateStatus(amTrainningFeedback);
	}
	
	/**
	 * 删除数据
	 * @param amTrainningFeedback
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmTrainningFeedback amTrainningFeedback) {
		super.delete(amTrainningFeedback);
	}
	public int findHasSubmitted(String trainningCode,String writtenBy){
		return feedbackDao.findHasSubmitted(trainningCode,writtenBy);
	}
	@Transactional(readOnly=false)
	public int deleteDb(String pkey) {
		return feedbackDao.deleteDb(pkey);
	}
	@Transactional(readOnly=false)
	public Boolean deleteData(String pkey) {
		Boolean flag=true;
		String[] str = pkey.split(",");
		for (int i = 0; i < str.length; i++) {
			this.deleteDb(str[i]);
		}
		return flag;
	}

	
}