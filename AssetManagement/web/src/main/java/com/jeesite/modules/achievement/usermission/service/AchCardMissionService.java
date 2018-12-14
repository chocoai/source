/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermission.service;

import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.usermission.entity.AchCardMission;
import com.jeesite.modules.achievement.usermission.dao.AchCardMissionDao;

/**
 * 绩效卡关键任务Service
 * @author PhilipGuan
 * @version 2018-11-21
 */
@Service
@Transactional(readOnly=true)
public class AchCardMissionService extends CrudService<AchCardMissionDao, AchCardMission> {

	@Autowired
	private AchCardMissionDao achCardMissionDao;
	@Autowired
	private AchCardScoreService achCardScoreService;

	/**
	 * 获取单条数据
	 * @param achCardMission
	 * @return
	 */
	@Override
	public AchCardMission get(AchCardMission achCardMission) {
		return super.get(achCardMission);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achCardMission
	 * @return
	 */
	@Override
	public Page<AchCardMission> findPage(Page<AchCardMission> page, AchCardMission achCardMission) {
		return super.findPage(page, achCardMission);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achCardMission
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchCardMission achCardMission) {
		super.save(achCardMission);
		achCardScoreService.updateMissionScore(achCardMission.getCardCode());
	}
	
	/**
	 * 更新状态
	 * @param achCardMission
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchCardMission achCardMission) {
		super.updateStatus(achCardMission);
	}
	
	/**
	 * 删除数据
	 * @param achCardMission
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchCardMission achCardMission) {
		achCardMissionDao.deleteData(achCardMission.getMissionCode(), achCardMission.getUserId());
	}

	/**
	 * 更新单据状态
	 * @param dataStatus
	 * @param updateBy
	 * @param cardCode
	 * @param userId
	 */
	@Transactional(readOnly=false)
	public void updateStatus(String dataStatus, String updateBy, String cardCode, String userId) {
		achCardMissionDao.updateStatus(dataStatus,updateBy,cardCode,userId);
	}
}