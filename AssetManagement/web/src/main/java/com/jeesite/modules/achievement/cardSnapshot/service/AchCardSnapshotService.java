/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardSnapshot.service;

import com.youzan.open.sdk.util.json.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshot;
import com.jeesite.modules.achievement.cardSnapshot.dao.AchCardSnapshotDao;

/**
 * 绩效卡快照Service
 * @author Philip Guan
 * @version 2018-12-04
 */
@Service
@Transactional(readOnly=true)
public class AchCardSnapshotService extends CrudService<AchCardSnapshotDao, AchCardSnapshot> {
	
	/**
	 * 获取单条数据
	 * @param achCardSnapshot
	 * @return
	 */
	@Override
	public AchCardSnapshot get(AchCardSnapshot achCardSnapshot) {
		return super.get(achCardSnapshot);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achCardSnapshot
	 * @return
	 */
	@Override
	public Page<AchCardSnapshot> findPage(Page<AchCardSnapshot> page, AchCardSnapshot achCardSnapshot) {
		return super.findPage(page, achCardSnapshot);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achCardSnapshot
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchCardSnapshot achCardSnapshot) {

		AchCardSnapshot item = new AchCardSnapshot();
		item.setCardCode(achCardSnapshot.getCardCode());
		item.setDataStatus(achCardSnapshot.getDataStatus());
		item.setDepartCode(achCardSnapshot.getDepartCode());
		item.setDepartName(achCardSnapshot.getDepartName());
		item.setExamineMonth(achCardSnapshot.getExamineMonth());
		item.setPostCode(achCardSnapshot.getPostCode());
		item.setPostName(achCardSnapshot.getPostName());
		item.setUserId(achCardSnapshot.getUserId());
		item.setPostName(achCardSnapshot.getUserName());

		item.setData(JsonUtils.toJson(achCardSnapshot));


		super.save(achCardSnapshot);
	}

	@Transactional(readOnly=false)
	public void saveData(String cardCode, String userId, String month, String dataStatus, String data, String dataType) {

		AchCardSnapshot item = new AchCardSnapshot();
		item.setCardCode(cardCode);
		item.setDataStatus(dataStatus);
		//item.setDepartCode(achCard.getDepartCode());
		//item.setDepartName(achCard.getDepartName());
		item.setExamineMonth(month);
		item.setDataType(dataType);
		//item.setPostCode(achCard.getPostCode());
		//item.setPostName(achCard.getPostName());
		item.setUserId(userId);
		//item.setPostName(achCard.getUserName());

		item.setData(data);


		super.save(item);
	}
	
	/**
	 * 更新状态
	 * @param achCardSnapshot
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchCardSnapshot achCardSnapshot) {
		super.updateStatus(achCardSnapshot);
	}
	
	/**
	 * 删除数据
	 * @param achCardSnapshot
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchCardSnapshot achCardSnapshot) {
		super.delete(achCardSnapshot);
	}
	
}