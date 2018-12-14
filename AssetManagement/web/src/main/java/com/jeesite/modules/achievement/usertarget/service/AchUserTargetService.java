/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usertarget.service;

import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import com.jeesite.modules.achievement.target.entity.AchTarget;
import com.jeesite.modules.achievement.target.service.AchTargetService;
import com.jeesite.modules.achievement.usertarget.dao.AchUserTargetDao;
import com.jeesite.modules.achievement.usertarget.entity.AchUserTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;

/**
 * 绩效指标管理Service
 * @author PhilipGuan
 * @version 2018-11-21
 */
@Service
@Transactional(readOnly=true)
public class AchUserTargetService extends CrudService<AchUserTargetDao, AchUserTarget> {

	@Autowired
	AchUserTargetDao achUserTargetDao;

	@Autowired
	private AchTargetService achTargetService;
	@Autowired
	private AchCardScoreService achCardScoreService;

	/**
	 * 获取单条数据
	 * @param achUserTarget
	 * @return
	 */
	@Override
	public AchUserTarget get(AchUserTarget achUserTarget) {
		return super.get(achUserTarget);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achUserTarget
	 * @return
	 */
	@Override
	public Page<AchUserTarget> findPage(Page<AchUserTarget> page, AchUserTarget achUserTarget) {
		return super.findPage(page, achUserTarget);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achUserTarget
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchUserTarget achUserTarget) {

		if(achUserTarget.getIsNewRecord()){
			//检查是否存在指标库，不存在则新增
			AchTarget achTarget = new AchTarget();
			achTarget.setTargetName(achUserTarget.getTargetName());
			long dbItemCount = achTargetService.findCount(achTarget);
			if(dbItemCount == 0){
				//achTarget.setTargetLevel(achUserTarget.getTargetLevel());
				achTarget.setBasicAims(achUserTarget.getBasicAims());
				achTarget.setBasicsDown(achUserTarget.getBasicsDown());
				achTarget.setBasicsUpstream(achUserTarget.getBasicsUpstream());
				achTarget.setBottom(achUserTarget.getBottom());
				achTarget.setChallengeGoal(achUserTarget.getChallengeGoal());
				achTarget.setCoefficientRange(achUserTarget.getCoefficientRange());
				achTarget.setDataSources(achUserTarget.getDataSources());
				achTarget.setDescription(achUserTarget.getDescription());
				achTarget.setFormula(achUserTarget.getFormula());
				achTarget.setScoreCoefficient(achUserTarget.getScoreCoefficient());
				achTarget.setStandardScore(achUserTarget.getStandardScore());
				achTarget.setTargetSettingContent(achUserTarget.getTargetSettingContent());
				achTarget.setTargetType(achUserTarget.getTargetType());
				achTarget.setTargetLevel("3");	//个人
				achTarget.setCreateBy(achUserTarget.getUserId());
				achTargetService.save(achTarget);
			}
		}
        achUserTarget.setTargetLevel("3");  //新增时默认是个人
		try{
			super.save(achUserTarget);
			achCardScoreService.updateTargerScore(achUserTarget.getCardCode());

		}
		catch (Exception ex){
			ex.printStackTrace();
		}




	}
	
	/**
	 * 更新状态
	 * @param achUserTarget
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchUserTarget achUserTarget) {
		super.updateStatus(achUserTarget);
	}
	
	/**
	 * 删除数据
	 * @param achUserTarget
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchUserTarget achUserTarget) {
		achUserTargetDao.deleteData(achUserTarget.getUserTargetCode(), achUserTarget.getUserId());
	}

	/**
	 * 创建默认绩效指标
	 * @param cardCode
	 * @param staffCode
	 * @param userName
	 */
	@Transactional(readOnly=false)
	public void createDefaultData(String cardCode, String staffCode, String userName) {
		achUserTargetDao.createDefaultData(cardCode,staffCode,userName, "0");
	}

	/**
	 * 更新单据状态
	 * @param status
	 * @param updateBy
	 * @param cardCode
	 * @param userId
	 */
	@Transactional(readOnly=false)
	public void updateStatus(String status, String updateBy, String cardCode, String userId) {
		achUserTargetDao.updateStatus(status,updateBy,cardCode,userId);
	}
}