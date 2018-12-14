/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationFollow;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationRecord;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.appreciation.entity.FzLeaderboards;
import com.jeesite.modules.fz.appreciation.dao.FzLeaderboardsDao;

/**
 * fz_leaderboardsService
 * @author dwh
 * @version 2018-09-26
 */
@Service
@Transactional(readOnly=true)
public class FzLeaderboardsService extends CrudService<FzLeaderboardsDao, FzLeaderboards> {
	@Autowired
	private FzAppreciationRecordService fzAppreciationRecordService;
	@Autowired
	private FzLeaderboardsDao fzLeaderboardsDao;
	/**
	 * 获取单条数据
	 * @param fzLeaderboards
	 * @return
	 */
	@Override
	public FzLeaderboards get(FzLeaderboards fzLeaderboards) {
		return super.get(fzLeaderboards);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzLeaderboards
	 * @return
	 */
	@Override
	public Page<FzLeaderboards> findPage(Page<FzLeaderboards> page, FzLeaderboards fzLeaderboards) {
		return super.findPage(page, fzLeaderboards);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzLeaderboards
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzLeaderboards fzLeaderboards) {
		super.save(fzLeaderboards);
	}
	
	/**
	 * 更新状态
	 * @param fzLeaderboards
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzLeaderboards fzLeaderboards) {
		super.updateStatus(fzLeaderboards);
	}
	
	/**
	 * 删除数据
	 * @param fzLeaderboards
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzLeaderboards fzLeaderboards) {
		super.delete(fzLeaderboards);
	}


	@Transactional
	public Page<LeaderboardData> getList(Integer pageSize,Integer pageNo,Integer flag,String type) throws ParseException {
		if (pageSize==null){
			pageSize=100;
		}
		if (pageNo==null){
			pageNo=1;
		}
//		String type=null;
		//1表示周，2表示月，3表示年,4表示季度
		Date startTime = getStartTime(flag);
		Date endTime = getEndTime(flag);
		List<FzAppreciationRecord> fzAppreciationRecords = null;
		//根据条件查找出时间段类的赞赏记录
		if (!ParamentUntil.isBackString(type)) {
			fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTime(startTime, endTime,pageSize,pageNo);
		} else {
			fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTimeAndType(startTime, endTime, type,pageSize,pageNo);
		}
		List<LeaderboardData> leaderboardDataList = prosses(fzAppreciationRecords);
//        int total=fzAppreciationRecordService.getTotal(startTime,endTime,type);
		Page<LeaderboardData> page=new Page<>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setCount(leaderboardDataList.size());
		page.setList(leaderboardDataList);

		return page;
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
			boolean ishave=false;
			//根据赞赏记录找到子记录
			List<FzAppreciationFollow> fzAppreciationFollows = fzAppreciationRecordService.getFollowsByCode(list.get(i).getAppreciationCode());
			//找到获赞者信息，头像，图片等
			String praiserId = list.get(i).getPraiserId();   //获赞者Id
			LeaderboardData leaderboardData = fzAppreciationRecordService.getUserInfo(praiserId);
			if (leaderboardData == null) {
				continue;
			}
			leaderboardData.setAppreciationCode(list.get(i).getAppreciationCode());
			long countNumber = sumCountNumber(fzAppreciationFollows, list.get(i));  //记录表的获赠币加赞赏表的获赠币
			if (ParamentUntil.isBackList(leaderboardDataList)){
				for (int j=0;j<leaderboardDataList.size();j++){
					if (list.get(i).getPraiserId().equals(leaderboardDataList.get(j).getUserid())){
						ishave=true;
						leaderboardDataList.get(j).setPraiserNumber(leaderboardDataList.get(j).getPraiserNumber()+list.get(i).getCoinNumber());
						break;
					}
				}
			}
			leaderboardData.setPraiserNumber(countNumber);
			if (ishave){
				continue;
			}
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

	// 删除临时表数据，重新插入
	@Transactional(readOnly = false)
	public void insertTemp() throws ParseException{
		Date startDate = getStartTime(3);
		Date endDate = getEndTime(3);
		// 先删除临时表数据
		fzLeaderboardsDao.truncateTemp();
		// 重新插入数据
		fzLeaderboardsDao.insertTemp(startDate, endDate);
	}


	/**
	 * 根据周，月，年，季度查询排行
	 * @param flag
	 * @return
	 * @throws ParseException
	 */
	public List<LeaderboardData> getLeaderboardList (int flag) throws ParseException{
		List<LeaderboardData> list = ListUtils.newArrayList();
		Date startDate = getStartTime(flag);
		Date endDate = getEndTime(flag);
		String start = DateUtils.formatDate(startDate, "yyyyMMdd");
		String end = DateUtils.formatDate(endDate, "yyyyMMdd");
		if (flag == 1) {
			// 周
			list = fzLeaderboardsDao.getWeekQuarterList(start, end);
		} else if (flag == 2) {
			// 当前年月
			String yearMonth = DateUtils.getYear() + DateUtils.getMonth();
			list = fzLeaderboardsDao.getYearMonthList(yearMonth);
		} else if (flag == 3){
			// 年
			String year = DateUtils.getYear();
			list = fzLeaderboardsDao.getYearMonthList(year);
		} else if (flag == 4) {
			// 获取季度
			list = fzLeaderboardsDao.getWeekQuarterList(start, end);
		}

		int sotNo = 1;
		for (LeaderboardData leaderboard : list) {
			leaderboard.setSotNo(sotNo++);
		}
		return list;
	}

	/**
	 * 根据时间和标签获取数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LeaderboardData> getLeaderboardByType(Date startTime, Date endTime, String type) {
		return fzLeaderboardsDao.getLeaderboardByType(startTime, endTime, type);
	}
}