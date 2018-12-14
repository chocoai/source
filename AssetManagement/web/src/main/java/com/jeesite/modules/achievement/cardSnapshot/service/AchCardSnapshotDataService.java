/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardSnapshot.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.card.entity.AchCardData;
import com.jeesite.modules.achievement.cardSnapshot.dao.AchCardSnapshotDao;
import com.jeesite.modules.achievement.cardSnapshot.dao.AchCardSnapshotDataDao;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshot;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshotData;
import com.youzan.open.sdk.util.json.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 绩效卡快照Service
 * @author Philip Guan
 * @version 2018-12-04
 */
@Service
@Transactional(readOnly=true)
public class AchCardSnapshotDataService extends CrudService<AchCardSnapshotDataDao, AchCardSnapshotData> {
	
	/**
	 * 获取单条数据
	 * @param achCardSnapshot
	 * @return
	 */
	@Override
	public AchCardSnapshotData get(AchCardSnapshotData achCardSnapshot) {
		return super.get(achCardSnapshot);
	}

	/**
	 * 获取快照数据
	 * @param cardCode
	 * @param newDataStatus
	 * @return
	 */
	public AchCardSnapshotData getSnapshotData(String cardCode, String newDataStatus, boolean canGetSnapshot){

		return canGetSnapshot ? getSnapshotData(cardCode, newDataStatus) : null;
	}

	/**
	 * 获取快照数据
	 * @param cardCode
	 * @param newDataStatus
	 * @return
	 */
	public AchCardSnapshotData getSnapshotData(String cardCode, String newDataStatus){
		AchCardSnapshotData snapshot = new AchCardSnapshotData();
		Integer dataStatus = Integer.parseInt(newDataStatus);
		//自评中-面谈中需要取快照
		if(dataStatus > 30 && dataStatus <= 50){
			AchCardSnapshotData snapshotQuery = new AchCardSnapshotData();
			snapshotQuery.setCardCode(cardCode);
			this.findList(snapshotQuery).stream().findFirst().ifPresent(a -> {
				//获取快照对象，然后赋值
				AchCardSnapshotData item = JsonMapper.fromJson(a.getData(), AchCardSnapshotData.class);
				if(item != null){
					item.getAchCardData().setDataStatus(newDataStatus);
					item.getAchCardMissions().forEach(b->b.setDataStatus(newDataStatus));
					item.getAchUserTargets().forEach(b->b.setDataStatus(newDataStatus));
					item.getAchCardSyntheticals().forEach(b->b.setDataStatus(newDataStatus));
					item.getAchCardScores().forEach(b->b.setDataStatus(newDataStatus));
					//snapshot.getAchCardMissions().forEach(a->a.setStatus(newDataStatus));
					BeanUtils.copyProperties(item, snapshot);
				}
				//dbData.setAchCardScores(a.getAchCardScores());
				//dbData.setAchCardSyntheticals(a.getAchCardSyntheticals());
				//dbData.setAchCardMissions(a.getAchCardMissions());
				//dbData.setAchUserTargets(a.getAchUserTargets());
			});
		}

		return snapshot;
	}
	
}