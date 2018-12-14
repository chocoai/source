/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.card.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.AchCardData;
import com.jeesite.modules.achievement.card.entity.AchCardGroupData;
import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshotData;
import com.jeesite.modules.achievement.cardSnapshot.service.AchCardSnapshotDataService;
import com.jeesite.modules.achievement.cardSnapshot.service.AchCardSnapshotService;
import com.jeesite.modules.achievement.cardscore.entity.AchCardScore;
import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import com.jeesite.modules.achievement.cardsynthetical.entity.AchCardSynthetical;
import com.jeesite.modules.achievement.cardsynthetical.service.AchCardSyntheticalService;
import com.jeesite.modules.achievement.log.service.AchLogService;
import com.jeesite.modules.achievement.usermission.entity.AchCardMission;
import com.jeesite.modules.achievement.usermission.service.AchCardMissionService;
import com.jeesite.modules.achievement.usertarget.entity.AchUserTarget;
import com.jeesite.modules.achievement.usertarget.service.AchUserTargetService;
import com.jeesite.modules.achievement.util.AchCardStatusUtils;
import com.jeesite.modules.asset.ding.entity.DepartmentUtil;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.util.DingDingAuth;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 绩效卡管理Controller
 * @author Philip
 * @version 2018-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/card")
public class AchCardController extends BaseController {

	@Autowired
	private AchCardService achCardService;
	@Autowired
	private AmSeqService amSeqService;
    @Autowired
    private AchLogService achLogService;

    @Autowired
    private AchUserTargetService achUserTargetService;
    @Autowired
    private AchCardMissionService achCardMissionService;
    @Autowired
    private AchCardSyntheticalService achCardSyntheticalService;
    @Autowired
    private AchCardScoreService achCardScoreService;
    @Autowired
    private AchCardSnapshotService achCardSnapshotService;
    @Autowired
    private AchCardSnapshotDataService achCardSnapshotDataService;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private AchCardStatusUtils achCardStatusUtils;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchCard get(String cardCode, boolean isNewRecord) {

		return achCardService.get(cardCode, isNewRecord);
	}
	
	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getCurrent")
	@IsFileter(isFile="true")
	//@AccessLimit(limit = 1,sec = 2)
	@ResponseBody
	public ReturnInfo getCurrent(AchCard achCard, HttpServletRequest request, HttpServletResponse response) {
		try {
            CardUsers userData = null;
            //TODO: 并发测试完成后需移除
            if(!StringUtils.isBlank(achCard.getCreateBy())){
                //用于并发测试
                userData = new CardUsers();
                String userId = achCard.getCreateBy();
                DingUser dingUser = dingUserService.getDingUser(userId);
                UserData currentUserData = dingUserService.getUserData(dingUser, null);
                userData.setCurrentUser(currentUserData);
            }
            else{
                userData = DingDingAuth.redisHelp.getUserInfo(request);
                if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");
            }


			AchCard query = new AchCard();
			query.setExamineMonth(achCard.getExamineMonth());

			ReturnInfo<AchCardData> result = null;
			if(userData.hasOtherUser){
                //存在非本人角色时，设置查询条件为传入的用户ID
                query.setExaminedStaffCode(achCard.getExaminedStaffCode());
                //代理时，可为被代理人创建绩效卡，否则只作查看
                result = achCardService.getOrAdd(query, userData.getAgentUser(), userData.isAgent, false);
            }else{
                //获取自己的绩效卡
                query.setExaminedStaffCode(userData.getCurrentUser().getUserid());
                result = achCardService.getOrAdd(query, userData.getCurrentUser(), true, true);
            }
			return result;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return ReturnDate.error(400, "新增绩效卡失败");

	}

	/**
	 * 更新状态
	 */
	@RequestMapping(value = "changeStatus")
	@IsFileter(isFile="true")
	//@AccessLimit(limit = 1,sec = 2)
	@ResponseBody
	public ReturnInfo changeStatus(AchCard achScore, HttpServletRequest request, HttpServletResponse response) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");


            userData.setQueryWithRequestUserIdIfNotSelf(achScore::setExaminedStaffCode, achScore.getExaminedStaffCode());

            String strDataStatus = achScore.getDataStatus();
            //如果是HR终审
            if(strDataStatus.equals("60")){
                achCardScoreService.updateScoreModifyScore(achScore.getCardCode(), achScore.getExamineMonth(), "3010");
            }
            else if(strDataStatus.equals("40")){
            	//如果是自评中,写入快照
                addSnapshot(achScore.getCardCode(), strDataStatus);
            }
            else {
                //流程未到HR终审，都设置为0
                int status = Integer.parseInt(achScore.getDataStatus());
                if(status < 60)
                    achCardScoreService.clearScoreModifyScore(achScore.getCardCode(), achScore.getExamineMonth(), "3010");
            }

			achCardService.updateStatus(achScore.getDataStatus(), userData.getCurrentUser().getUserid(), achScore.getCardCode(), achScore.getExaminedStaffCode());
            //achLogService.addLog(userData.getCurrentUser().getUserid(), userData.getCurrentUser().getName(), "新状态:"+achScore.getDataStatus(), "绩效卡状态变更");

			return ReturnDate.success();
		}
		catch (Exception e){

		}
		return ReturnDate.error(400, "新增绩效卡失败");

	}

	private void addSnapshot(String cardCode, String dataStatus){
        String dataTypeName = DictUtils.getDictLabel("ach_card_status", dataStatus, "");

        //已提交
        AchCardData cardData = achCardService.getDataById(cardCode);
        if(cardData!=null){
            AchUserTarget targetQuery = new AchUserTarget();
            AchCardMission missionQuery = new AchCardMission();
            AchCardSynthetical syntheticalQuery = new AchCardSynthetical();
            AchCardScore cardScoreQuery = new AchCardScore();
            targetQuery.setCardCode(cardCode);
            missionQuery.setCardCode(cardCode);
            syntheticalQuery.setCardCode(cardCode);
            cardScoreQuery.setCardCode(cardCode);

            List<AchUserTarget> achUserTargets = achUserTargetService.findList(targetQuery);
            List<AchCardMission> achCardMissions = achCardMissionService.findList(missionQuery);
            List<AchCardSynthetical> achCardSyntheticals = achCardSyntheticalService.findList(syntheticalQuery);
            List<AchCardScore> achCardScores = achCardScoreService.findList(cardScoreQuery);

            AchCardSnapshotData cardSnapshot = new AchCardSnapshotData();
            cardSnapshot.setAchCardData(cardData);
            //cardSnapshot.setAchCard(achCard); //不用配置
            cardSnapshot.setAchUserTargets(achUserTargets);
            cardSnapshot.setAchCardMissions(achCardMissions);
            cardSnapshot.setAchCardSyntheticals(achCardSyntheticals);
            cardSnapshot.setAchCardScores(achCardScores);
            String data = JsonMapper.toJson(cardSnapshot);
            achCardSnapshotService.saveData(cardCode, cardData.getExaminedStaffCode(), cardData.getExamineMonth(), dataStatus, data, dataTypeName);
        }
    }

    /**
     * 批量更新状态
     */
    @RequestMapping(value = "batchUpdateStatus")
    @IsFileter(isFile="true")
    @ResponseBody
    public ReturnInfo batchUpdateStatus(AchCard achScore, HttpServletRequest request, HttpServletResponse response) {
        try {
            CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
            if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

            UserData currentUser = userData.getOtherOrCurrentUser();
            if(currentUser == null) currentUser = userData.getCurrentUser();

            //根據當前部門獲取所有子部門
            List<String> myDeptIds = new ArrayList<>();
            String deptCode = achScore.getDepartCode();
			if(!StringUtils.isBlank(deptCode)){
				myDeptIds.add(deptCode);
				DepartmentUtil.getChildDepartmentIds(deptCode, null, myDeptIds);
			}
            String deptQuery = myDeptIds.size() == 0 ? "" : "AND b.department_id in ('"+String.join("','", myDeptIds)+"')";

            String expUserIds = userData.isManager ? "''" : "'" + currentUser.getUserid() + "'";  //去掉自己或被代理人的绩效卡，

            String userName = StringUtils.isBlank(achScore.getCreateByName()) ? null : "%"+achScore.getCreateByName()+"%";



            //String newStatusString = achScore.getStatus();
            //Integer.parseInt(newStatusString)
            String newStatus = achScore.getStatus();
            String oldStatus = achCardStatusUtils.getStatus(newStatus, StringUtils.equalsIgnoreCase(achScore.getAuditBy(), "1")); //auditBy=1:回退狀態

            achCardService.batchUpdateStatus(achScore.getExamineMonth(), achScore.getAssessorCode(), userName, achScore.getDataStatus(), deptQuery, expUserIds, newStatus, oldStatus);

            return ReturnDate.success();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ReturnDate.error(400, "更新绩效卡状态失败");

    }



	/**
	 * 保存绩效卡
	 */
    @PostMapping(value = "save")
    @IsFileter(isFile="true")
    @ResponseBody
    public ReturnInfo save(AchCard achCard, HttpServletRequest request) {
		CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
		if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

		if(StringUtils.isBlank(achCard.getCardCode()))
			return ReturnDate.error(400, "不存在该绩效卡");

		achCard.setIsNewRecord(false);
		achCard.setUpdateBy(userData.getCurrentUser().getUserid());
        achCard.setExamineScore(null);  //不允许提交考核得分
        achCard.setFinalScore(null);    //不允许提交最终得分

		achCardService.save(achCard);
        //achLogService.addLog(userData.getCurrentUser().getUserid(), userData.getCurrentUser().getName(), "修改绩效卡", JsonUtils.toJson(achCard));

        return ReturnDate.success();
	}
	
	/**
	 * 停用绩效卡
	 */
	@RequiresPermissions("ach:card:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AchCard achScore) {
		achScore.setStatus(AchCard.STATUS_DISABLE);
		achCardService.updateStatus(achScore);
		return renderResult(Global.TRUE, text("停用绩效卡成功"));
	}
	
	/**
	 * 启用绩效卡
	 */
	@RequiresPermissions("ach:card:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AchCard achScore) {
		achScore.setStatus(AchCard.STATUS_NORMAL);
		achCardService.updateStatus(achScore);
		return renderResult(Global.TRUE, text("启用绩效卡成功"));
	}
	
	/**
	 * 删除绩效卡
	 */
	@RequiresPermissions("ach:card:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AchCard achScore) {
		achCardService.delete(achScore);
		return renderResult(Global.TRUE, text("删除绩效卡成功！"));
	}



	/**
	 * 查询我的下属列表数据
	 */
	@RequestMapping(value = "getMySubordinate")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getMySubordinate(AchCard achScore, HttpServletRequest request, HttpServletResponse response) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

            UserData currentUser = userData.getOtherOrCurrentUser();

            List<String> myDeptIds = new ArrayList<>();


            //非管理员，判断是否有下属
            if(userData.isManager.equals(false)){
                boolean getAll = StringUtils.equalsIgnoreCase(achScore.getHadFollower(), "1");  //是否获取所有人
                //如果不是获取所有人，则循环所有该用户管理的部门
                if(!getAll) {
                    currentUser.getDingDepartmentList().stream().filter(a -> a.getOrgDeptOwner().equals(currentUser.getUserid())).findAny().ifPresent(a -> {
                        //获取该部门的子部门
                        DepartmentUtil.getChildDepartmentIds(a.getDepartmentId(), null, myDeptIds);
                        myDeptIds.add(a.getDepartmentId());
                    });
                    if(myDeptIds.size() == 0) return ReturnDate.error(400, "没有关联的部门");
                }

            }

            //传递部门参数，则只搜索该部门所有下属
            if(!StringUtils.isBlank(achScore.getDepartCode())){
                myDeptIds.add(achScore.getDepartCode());
                DepartmentUtil.getChildDepartmentIds(achScore.getDepartCode(), null, myDeptIds);
            }


            String deptQuery = myDeptIds.size() == 0 ? "" : "AND b.department_id in ('"+String.join("','", myDeptIds)+"')";

			String expUserIds = userData.isManager ? "''" : "'" + currentUser.getUserid() + "'";  //去掉自己或被代理人的绩效卡，
            String cannotShowAddScore = !userData.isManager && userData.isBoss ? "0" : null;    //是否不能显示加减分，0=不能显示，null=可显示
			//过滤上级进入本部门的情况,防止看到上级的绩效卡
			//List<String> managers = DepartmentUtil.getLeaderFromDepartmentIds(upDepts);
			//if(managers.size() >0){
			//	expUserIds = String.join(",", managers);
			//}

            String userName = StringUtils.isBlank(achScore.getCreateByName()) ? null : "%"+achScore.getCreateByName()+"%";

			Page<AchCardGroupData> page = new Page<>(request, response);
			Page<AchCardGroupData> list = achCardService.getGroupData(achScore.getExamineMonth(), achScore.getAssessorCode(), userName, achScore.getDataStatus(), deptQuery, expUserIds, cannotShowAddScore, page);
			return ReturnDate.successObject(list);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return ReturnDate.error(400, "获取绩效卡出现异常");

	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("ach:card:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchCard achCard, Model model) {
		model.addAttribute("achCard", achCard);
		return "achievement/card/achCardList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ach:card:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchCard> listData(AchCard achCard, HttpServletRequest request, HttpServletResponse response) {
		Page<AchCard> page = achCardService.findPage(new Page<AchCard>(request, response), achCard);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ach:card:view")
	@RequestMapping(value = "form")
	public String form(AchCard achCard, Model model) {

	    AchCardScore cardScore = new AchCardScore();
        cardScore.setCardCode(achCard.getCardCode());
        model.addAttribute("achCardScore", achCardScoreService.findList(cardScore));

        AchUserTarget userTarget = new AchUserTarget();
        userTarget.setCardCode(achCard.getCardCode());
        model.addAttribute("achUserTarget", achUserTargetService.findList(userTarget));

        AchCardMission cardMission = new AchCardMission();
        cardMission.setCardCode(achCard.getCardCode());
        model.addAttribute("achCardMission", achCardMissionService.findList(cardMission));

        AchCardSynthetical cardSynthetical = new AchCardSynthetical();
        cardSynthetical.setCardCode(achCard.getCardCode());
        model.addAttribute("achCardSynthetical", achCardSyntheticalService.findList(cardSynthetical));


		model.addAttribute("achCard", achCard);
		return "achievement/card/amOrderForm";
	}
}