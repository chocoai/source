/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.card.service;

import java.util.List;
import java.util.Optional;

import com.jeesite.modules.achievement.card.entity.AchCardData;
import com.jeesite.modules.achievement.card.entity.AchCardGroupData;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshotData;
import com.jeesite.modules.achievement.cardSnapshot.service.AchCardSnapshotDataService;
import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import com.jeesite.modules.achievement.cardscoremodify.service.AchCardScoreModifyService;
import com.jeesite.modules.achievement.cardsynthetical.service.AchCardSyntheticalService;
import com.jeesite.modules.achievement.log.service.AchLogService;
import com.jeesite.modules.achievement.target.entity.AchTarget;
import com.jeesite.modules.achievement.usermission.service.AchCardMissionService;
import com.jeesite.modules.achievement.usertarget.service.AchUserTargetService;
import com.jeesite.modules.asset.ding.entity.*;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.dao.AchCardDao;

import javax.annotation.Resource;

/**
 * 绩效卡Service
 * @author Philip Guan
 * @version 2018-11-19
 */
@Service
@Transactional(readOnly=true)
public class AchCardService extends CrudService<AchCardDao, AchCard> {

	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AchUserTargetService achUserTargetService;
	@Autowired
	private AchCardMissionService achCardMissionService;
	@Autowired
	private AchCardSyntheticalService achCardSyntheticalService;
	@Autowired
	private AchCardScoreModifyService achCardScoreModifyService;
	@Autowired
	private AchCardScoreService achCardScoreService;
	@Autowired
	private AchLogService achLogService;
	@Autowired
    private DingDepartmentService dingDepartmentService;
	@Autowired
    private DingUserService dingUserService;
    @Autowired
    private AchCardSnapshotDataService achCardSnapshotDataService;

	@Autowired
	private AchCardDao achCardDao;

	@Resource
	private RedisTemplate<String, List> redisTemplate;
	/**
	 * 获取单条数据
	 * @param achCard
	 * @return
	 */
	@Override
	public AchCard get(AchCard achCard) {
		return super.get(achCard);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achCard
	 * @return
	 */
	@Override
	public Page<AchCard> findPage(Page<AchCard> page, AchCard achCard) {
		return super.findPage(page, achCard);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchCard achCard) {
		super.save(achCard);
		achCardDao.updateFinalScore(achCard.getCardCode());
		//achCardScoreService.updateTargerScore(achCard.getCardCode());
	}
	
	/**
	 * 更新状态
	 * @param achCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchCard achCard) {
		super.updateStatus(achCard);
	}
	
	/**
	 * 删除数据
	 * @param achCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchCard achCard) {
		super.delete(achCard);
	}

	/**
	 * 获取或新增绩效卡
	 * @param query
	 * @param userData
	 */
	@Transactional(readOnly=false)
	public ReturnInfo<AchCardData> getOrAdd(AchCard query, UserData userData, boolean canAdd, boolean canGetSnapshot) {
		AchCardData item = achCardDao.getData(query.getExamineMonth(), query.getExaminedStaffCode());
		if(canAdd && item == null){

			if(userData.getDirectSuperior() == null || userData.getDirectSuperior().isEmpty())
				return ReturnDate.error(400, "没有配置直属上级");

			List<DictData> monthDictList = DictUtils.getDictList("ach_card_init");
			if(!monthDictList.stream().anyMatch(a->a.getDictValue().equals(query.getExamineMonth())))
				return ReturnDate.error(400,"未下发"+query.getExamineMonth()+"的绩效卡");

			AchCard dbItem = new AchCard();
			dbItem.setExamineWeight(100d);
			dbItem.setExamineMonth(query.getExamineMonth());
			dbItem.setCreateBy(userData.getUserid());
			//绑定部门
            Optional<DingDepartment> optionalDingDepartment = userData.getDingDepartmentList().stream().findFirst();
            if(optionalDingDepartment.isPresent()){
                DingDepartment dept = optionalDingDepartment.get();
                dbItem.setDepartCode(dept.getDepartmentId());
                dbItem.setDepartName(dept.getName());



                //List<DingUserDepartment> dingUserDepartmentList = redisTemplate.opsForValue().get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
                //// 获取缓存中所有部门
                //List<DepartmentData> departmentList = redisTemplate.opsForValue().get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);

                DingUser secondSuperior = dingUserService.getMyBoss(userData.getDirectSuperior());
                if(secondSuperior != null) {
                    dbItem.setSecondSuperiorCode(secondSuperior.getUserid());
                    dbItem.setSecondSuperiorName(secondSuperior.getName());
                }

                //DepartmentData firstDept = DepartmentUtil.getFirstDepartment(userData.getUserid(),dingUserDepartmentList,departmentList);
                String[] parentCodeList = dept.getParentCodes().split(",");
                String firstDeptCode = parentCodeList[parentCodeList.length-2];
                DingDepartment firstDept = null;
                if(firstDeptCode.equals("0")){
                    firstDept = userData.getDingDepartmentList().stream().findFirst().get();
                }
                else
                    firstDept = dingDepartmentService.get(firstDeptCode);
                if(firstDept == null)
                    return ReturnDate.error(400, "没有关联的一级部门");

                dbItem.setFirstDepartName(firstDept.getName());
                dbItem.setFirstDepartCode(firstDept.getDepartmentId());
            }
			else{
				return ReturnDate.error(400, "没有关联的部门");
			}

			dbItem.setExaminedStaffCode(userData.getUserid());	//绑定用户id
			dbItem.setPostCode(userData.getPosition());			//绑定职位id

			String strIsLeader = userData.getIsLeaderInDepts();
			if(!strIsLeader.equals(null) && !strIsLeader.isEmpty() && strIsLeader.contains(dbItem.getDepartCode()+":true")){
				dbItem.setHadFollower("1");
			}
			else{
				dbItem.setHadFollower("0");
			}

			dbItem.setAssessorCode(userData.getDirectSuperior());

			try{
				dbItem.setDataStatus("0");
				dbItem.setCardCode(amSeqService.getCode("JXK", "yyyyMM", 4));
				super.insert(dbItem);
				AchTarget achTarget = new AchTarget();
				achTarget.setTargetLevel("0");

				//创建默认绩效指标
				achUserTargetService.createDefaultData(dbItem.getCardCode(), userData.getUserid(), userData.getName());
				//创建默认综合指标
                achCardSyntheticalService.createDefaultData(dbItem.getCardCode(), userData.getUserid(), userData.getIsManager());
                //创建默认评分表
				achCardScoreService.createDefaultData(dbItem.getCardCode(), userData.getUserid(), userData.getDirectSuperior());



                achCardScoreService.updateTargerScore(dbItem.getCardCode());
                achCardScoreService.updateSyntheticalScore(dbItem.getCardCode());

				item = achCardDao.getData(query.getExamineMonth(), userData.getUserid());

                //写入日志
                achLogService.addLog(userData.getUserid(), userData.getName(), "创建绩效卡", "创建绩效卡:" + dbItem.getCardCode(), null);
			}
			catch (Exception ex){
				ex.printStackTrace();
				throw ex;
			}
			finally {
			    //如果是新增，则不能获取快照
			    canGetSnapshot = false;
            }
		}

		//HR初审-面谈中需要取快照(本人)
        AchCardSnapshotData snapshot = achCardSnapshotDataService.getSnapshotData(item.getCardCode(), item.getDataStatus(), canGetSnapshot);
        if (snapshot != null && snapshot.getAchCardData() != null) {
            item = snapshot.getAchCardData();
        }

        if(item == null)
        	return ReturnDate.error(400, "获取绩效卡,请刷新界面后重试或联系管理员");

		return ReturnDate.successObject(item);
	}

    @Transactional(readOnly=true)
    public AchCardData getDataById(String cardCode) {
        return achCardDao.getDataById(cardCode);
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
		achCardDao.updateStatus(dataStatus,updateBy,cardCode,userId);
		achUserTargetService.updateStatus(dataStatus,updateBy,cardCode,userId);
		achCardMissionService.updateStatus(dataStatus,updateBy,cardCode,userId);
		achCardSyntheticalService.updateStatus(dataStatus,updateBy,cardCode,userId);
		achCardScoreModifyService.updateStatus(dataStatus,updateBy,cardCode,userId);
	}

    /**
     * 分页获取统计数据
     * @param month
     * @param userId
     * @param userName
     * @param dataStatus
     * @param myDeptIds
     * @param expUserIds
     * @param page
     * @return
     */
	@Transactional(readOnly=true)
	public Page<AchCardGroupData> getGroupData(String month, String userId, String userName, String dataStatus, String myDeptIds, String expUserIds, String canShowAddScore, Page<AchCardGroupData> page) {
		int startIndex = (page.getPageNo()-1)*page.getPageSize();
		page.setList(achCardDao.getGroupData(month, userId, userName, dataStatus, myDeptIds, expUserIds, canShowAddScore, startIndex, page.getPageSize()));
		page.setCount(achCardDao.getGroupDataCount(month, userId, userName, dataStatus, myDeptIds, expUserIds));
		return page;
	}

    /**
     * 批量更新单据状态
     * @param month
     * @param deptQuery
     * @param expUserIds
     */
    @Transactional(readOnly=false)
    public void batchUpdateStatus(String month, String userId, String userName, String dataStatus, String deptQuery, String expUserIds, String newDataStatus, String oldDataStatus) {
        achCardDao.batchUpdateCardStatus("ach_card", month, userId, userName, dataStatus, deptQuery, expUserIds, newDataStatus, oldDataStatus);
        achCardDao.batchUpdateStatus("ach_card_mission", month, userId, userName, dataStatus, deptQuery, expUserIds, newDataStatus, oldDataStatus);
        achCardDao.batchUpdateStatus("ach_user_target", month, userId, userName, dataStatus, deptQuery, expUserIds, newDataStatus, oldDataStatus);
        achCardDao.batchUpdateStatus("ach_card_synthetical", month, userId, userName, dataStatus, deptQuery, expUserIds, newDataStatus, oldDataStatus);
        achCardDao.batchUpdateStatus("ach_card_score", month, userId, userName, dataStatus, deptQuery, expUserIds, newDataStatus, oldDataStatus);
		//如果是HR终审通过(60->100)更新最终得分
		if(newDataStatus.equals("100")){
			achCardDao.batchUpdateFinalScore(month, userId, userName, oldDataStatus, deptQuery, expUserIds);
		}
    }

	/**
	 * 批量更新最终得分(终审时)
	 * @param month
	 * @param userId
	 * @param userName
	 * @param dataStatus
	 * @param myDeptIds
	 * @param expUserIds
	 */
	public void batchUpdateFinalScore(String month, String userId, String userName, String dataStatus, String myDeptIds, String expUserIds) {
		achCardDao.batchUpdateFinalScore(month, userId, userName, dataStatus, myDeptIds, expUserIds);
	}

}