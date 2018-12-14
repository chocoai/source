/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationFollow;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationRecord;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationType;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationRecordService;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationTypeService;
import com.jeesite.modules.fz.config.AccessLimit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.fz.appreciation.entity.FzLeaderboards;
import com.jeesite.modules.fz.appreciation.service.FzLeaderboardsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * fz_leaderboardsController
 * @author dwh
 * @version 2018-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/appreciation/fzLeaderboards")
public class FzLeaderboardsController extends BaseController {

	@Autowired
	private FzAppreciationRecordService fzAppreciationRecordService;
	@Autowired
	private FzLeaderboardsService fzLeaderboardsService;
	@Autowired
	private FzAppreciationTypeService appreciationTypeService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzLeaderboards get(String leaderboardCode, boolean isNewRecord) {
		return fzLeaderboardsService.get(leaderboardCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("appreciation:fzLeaderboards:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzLeaderboards fzLeaderboards, Model model) {
		List<FzAppreciationType> fzAppreciationTypes=appreciationTypeService.findList(new FzAppreciationType());
		model.addAttribute("fzAppreciationTypes",fzAppreciationTypes);
		model.addAttribute("fzLeaderboards", fzLeaderboards);
		return "fz/appreciation/fzLeaderboardsList";
	}
	
	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("appreciation:fzLeaderboards:view")
//	@RequestMapping(value = "listData")
//	@ResponseBody
//	public Page<FzLeaderboards> listData(FzLeaderboards fzLeaderboards, HttpServletRequest request, HttpServletResponse response,String flag,String type) {
//		int pageSize=100;
//		int pageNo=1;
//		//1表示周，2表示月，3表示年,4表示季度
//		Date startTime = getStartTime(flag);
//		Date endTime = getEndTime(flag);
//		List<FzAppreciationRecord> fzAppreciationRecords = null;
//		//根据条件查找出时间段类的赞赏记录
//		if (!ParamentUntil.isBackString(type)) {
//			fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTime(startTime, endTime,pageSize,pageNo);
//		} else {
//			fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTimeAndType(startTime, endTime, type,pageSize,pageNo);
//		}
//		List<LeaderboardData> leaderboardDataList = prosses(fzAppreciationRecords);
//		Page<FzLeaderboards> page = fzLeaderboardsService.findPage(new Page<FzLeaderboards>(request, response), fzLeaderboards);
//		return page;
//	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("appreciation:fzLeaderboards:view")
	@RequestMapping(value = "form")
	public String form(FzLeaderboards fzLeaderboards, Model model) {
		model.addAttribute("fzLeaderboards", fzLeaderboards);
		return "fz/appreciation/fzLeaderboardsForm";
	}

	/**
	 * 保存fz_leaderboards
	 */
	@RequiresPermissions("appreciation:fzLeaderboards:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzLeaderboards fzLeaderboards) {
		fzLeaderboardsService.save(fzLeaderboards);
		return renderResult(Global.TRUE, text("保存fz_leaderboards成功！"));
	}
	
	/**
	 * 删除fz_leaderboards
	 */
	@RequiresPermissions("appreciation:fzLeaderboards:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzLeaderboards fzLeaderboards) {
		fzLeaderboardsService.delete(fzLeaderboards);
		return renderResult(Global.TRUE, text("删除fz_leaderboards成功！"));
	}
	private Date getStartTime(Integer flag) throws ParseException {
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = null;
		if (flag == null) {
			startTime = TimeUtils.getBeginDayOfMonth();
		} else if (flag == 1) {
			startTime = TimeUtils.getBeginDayOfWeek();
		} else if (flag == 2) {
			startTime = TimeUtils.getBeginDayOfMonth();
		} else if (flag == 3) {
			startTime = TimeUtils.getBeginDayOfYear();
		} else if (flag == 4) {
			Integer i = Integer.parseInt(TimeUtils.getQuarter()); //得到当前第几季度，1.2.3.4.
			startTime = longSdf.parse(TimeUtils.getCurrQuarter(i)[0]);
		}
		return startTime;
	}

	private List<LeaderboardData> prosses(List<FzAppreciationRecord> list) {
		if (!ParamentUntil.isBackList(list)) {
			return null;
		}
		List<LeaderboardData> leaderboardDataList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			//根据赞赏记录找到子记录
			List<FzAppreciationFollow> fzAppreciationFollows = fzAppreciationRecordService.getFollowsByCode(list.get(i).getAppreciationCode());
			//找到获赞者信息，头像，图片等
			String praiserId = list.get(i).getPraiserId();   //获赞者Id
			LeaderboardData leaderboardData = fzAppreciationRecordService.getUserInfo(praiserId);
			if (leaderboardData == null) {
				continue;
			}
			long countNumber = sumCountNumber(fzAppreciationFollows, list.get(i));  //记录表的获赠币加赞赏表的获赠币
			leaderboardData.setPraiserNumber(countNumber);
			leaderboardDataList.add(leaderboardData);
		}
		leaderboardDataList = getSortList(leaderboardDataList);
		return leaderboardDataList;
	}

	private long sumCountNumber(List<FzAppreciationFollow> fzAppreciationFollows, FzAppreciationRecord appreciationRecord) {
		long rst = appreciationRecord.getCoinNumber();
		if (!ParamentUntil.isBackList(fzAppreciationFollows)) {
			return appreciationRecord.getCoinNumber();
		}
		for (int i = 0; i < fzAppreciationFollows.size(); i++) {
			rst = rst + fzAppreciationFollows.get(i).getCoinNumber();
		}
		return rst;
	}

	public static List<LeaderboardData> getSortList(List<LeaderboardData> list) {
		Collections.sort(list, new Comparator<LeaderboardData>() {
			@Override
			public int compare(LeaderboardData o1, LeaderboardData o2) {
				if (o2.getPraiserNumber() > o1.getPraiserNumber()) {
					return 1;
				}
				if (o2.getPraiserNumber() == o1.getPraiserNumber()) {
					return 0;
				}
				return -1;
			}
		});
		return list;
	}
	private Date getEndTime(Integer flag) throws ParseException {
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endTime = null;
		if (flag == null) {
			endTime = TimeUtils.getEndDayOfMonth();
		} else if (flag == 1) {
			endTime = TimeUtils.getEndDayOfWeek();
		} else if (flag == 2) {
			endTime = TimeUtils.getEndDayOfMonth();
		} else if (flag == 3) {
			endTime = TimeUtils.getEndDayOfYear();
		} else if (flag == 4) {
			Integer i = Integer.parseInt(TimeUtils.getQuarter()); //得到当前第几季度，1.2.3.4.
			endTime = longSdf.parse(TimeUtils.getCurrQuarter(i)[1]);   //得到借宿时间
		}
		return endTime;
	}
}