/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardsynthetical.service;

import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.cardsynthetical.entity.AchCardSynthetical;
import com.jeesite.modules.achievement.cardsynthetical.dao.AchCardSyntheticalDao;

/**
 * 绩效卡综合管理Service
 * @author Philip Guan
 * @version 2018-11-21
 */
@Service
@Transactional(readOnly=true)
public class AchCardSyntheticalService extends CrudService<AchCardSyntheticalDao, AchCardSynthetical> {

	@Autowired
	private AchCardSyntheticalDao achCardSyntheticalDao;
	@Autowired
	private AchCardScoreService achCardScoreService;

	/**
	 * 获取单条数据
	 * @param achCardSynthetical
	 * @return
	 */
	@Override
	public AchCardSynthetical get(AchCardSynthetical achCardSynthetical) {
		return super.get(achCardSynthetical);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achCardSynthetical
	 * @return
	 */
	@Override
	public Page<AchCardSynthetical> findPage(Page<AchCardSynthetical> page, AchCardSynthetical achCardSynthetical) {
		return super.findPage(page, achCardSynthetical);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achCardSynthetical
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchCardSynthetical achCardSynthetical) {
		super.save(achCardSynthetical);
		achCardScoreService.updateSyntheticalScore(achCardSynthetical.getCardCode());
	}
	
	/**
	 * 更新状态
	 * @param achCardSynthetical
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchCardSynthetical achCardSynthetical) {
		super.updateStatus(achCardSynthetical);
	}
	
	/**
	 * 删除数据
	 * @param achCardSynthetical
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchCardSynthetical achCardSynthetical) {
		super.delete(achCardSynthetical);
	}

	/**
	 * 创建默认综合指标
	 * @param cardCode
	 * @param staffCode
	 * @param isManager
	 */
	@Transactional(readOnly=false)
	public void createDefaultData(String cardCode, String staffCode, String isManager) {
		achCardSyntheticalDao.createDefaultData(cardCode,staffCode,isManager);
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
		achCardSyntheticalDao.updateStatus(dataStatus,updateBy,cardCode,userId);
	}
}