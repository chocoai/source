/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.ReadFile;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.ExportAppreciationData;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.scheduledtask.K3Config;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.appreciation.dao.FzAppreciationFollowDao;
import com.jeesite.modules.fz.appreciation.dao.FzAppreciationRecordDao;
import com.jeesite.modules.fz.appreciation.entity.*;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import com.jeesite.modules.fz.expendrecord.dao.FzExpenditureRecordDao;
import com.jeesite.modules.fz.fzgoldchangerecord.entity.FzGoldChangeRecord;
import com.jeesite.modules.fz.fzgoldchangerecord.service.FzGoldChangeRecordService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.jeesite.modules.fz.appreciation.entity.FollowRecordData;

/**
 * 赞赏记录表Service
 *
 * @author dwh
 * @version 2018-09-19
 */
@Service
@Transactional(readOnly = true)
public class FzAppreciationRecordService extends CrudService<FzAppreciationRecordDao, FzAppreciationRecord> {
    private static final String FZBGCORD_PER_FIX = "FZBG";
    @Autowired
    private FzAppreciationFollowDao fzAppreciationFollowDao;
    @Autowired
    private FzAppreciationRecordDao fzAppreciationRecordDao;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private FzGoldChangeRecordService fzGoldChangeRecordService;

    @Autowired
    private AmSeqService amSeqService;
    @Value("${file.baseDir}")
    String baseDir;

    /**
     * 获取单条数据
     *
     * @param fzAppreciationRecord
     * @return
     */
    @Override
    public FzAppreciationRecord get(FzAppreciationRecord fzAppreciationRecord) {
        FzAppreciationRecord entity = super.get(fzAppreciationRecord);
        if (entity != null) {
            FzAppreciationFollow fzAppreciationFollow = new FzAppreciationFollow(entity);
            fzAppreciationFollow.setStatus(FzAppreciationFollow.STATUS_NORMAL);
            entity.setFzAppreciationFollowList(fzAppreciationFollowDao.findList(fzAppreciationFollow));
        }
        return entity;
    }

    /**
     * 查询分页数据
     *
     * @param page                 分页对象
     * @param fzAppreciationRecord
     * @return
     */
    @Override
    public Page<FzAppreciationRecord> findPage(Page<FzAppreciationRecord> page, FzAppreciationRecord fzAppreciationRecord) {
        return super.findPage(page, fzAppreciationRecord);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param fzAppreciationRecord
     */
    @Transactional(readOnly = false)
    public void save(FzAppreciationRecord fzAppreciationRecord, DingUser praiserUser, DingUser presenterUser, String outGoldType) {
        //赞币总数量默认为赠送数量
        long coin_count = fzAppreciationRecord.getCoinNumber();
        fzAppreciationRecord.setCoinCount(coin_count);
        super.save(fzAppreciationRecord);
        // 保存 FzAppreciationRecord子表
        if (fzAppreciationRecord.getFzAppreciationFollowList().size() > 0 && ParamentUntil.isBackString(fzAppreciationRecord.getFzAppreciationFollowList().get(0).getPresenterId())) {
            for (FzAppreciationFollow fzAppreciationFollow : fzAppreciationRecord.getFzAppreciationFollowList()) {
                if (!FzAppreciationFollow.STATUS_DELETE.equals(fzAppreciationFollow.getStatus())) {
                    fzAppreciationFollow.setAppreciationCode(fzAppreciationRecord);
                    if (fzAppreciationFollow.getIsNewRecord()) {
                        fzAppreciationFollow.preInsert();
                        fzAppreciationFollowDao.insert(fzAppreciationFollow);
                    } else {
                        fzAppreciationFollow.preUpdate();
                        fzAppreciationFollowDao.update(fzAppreciationFollow);
                    }
                } else {
                    fzAppreciationFollowDao.delete(fzAppreciationFollow);
                }
            }
        }

        //赠赞者，减
        dingUserService.save(presenterUser);
        //增加梵钻支出变更记录
        addRecord("1", presenterUser.getUserid(), coin_count, outGoldType, 1);
        //获赞者，加
        dingUserService.save(praiserUser);
        //增加梵钻收入变更记录
        addRecord("0", praiserUser.getUserid(), coin_count, "0", 1);


		// 文件
//		String path = baseDir + "/fz/fzAppreciationRecord.txt";
		String path = K3Config.BASEDIR +  "/" + FzTask.sendMsgFlag + "/fzAppreciationRecord.txt";
		// 获取的标识
		String flag = ReadFile.ReadToString(path);
		// false 代表不执行 true 代表执行
		if ("true".equals(flag)) {
			rabbitTemplate.convertAndSend(FzTask.fzAppreciationQueueP, fzAppreciationRecord);
		}


//		//增加梵钻支出变更记录
//		String code =amSeqService.getCode(FZBGCORD_PER_FIX);
//		FzGoldChangeRecord fzGoldChangeRecord1=new FzGoldChangeRecord();
//		fzGoldChangeRecord1.setRecordCode(code);
//		fzGoldChangeRecord1.setInOrOut("1");
//		fzGoldChangeRecord1.setGoldType(outGoldType);
//		fzGoldChangeRecord1.setBalance((long) 0);
//		fzGoldChangeRecord1.setNumber(coin_count);  //变更数量为赠送数量
//		fzGoldChangeRecord1.setUserid(presenterUser.getUserid());
//		fzGoldChangeRecordService.save(fzGoldChangeRecord1);
//		//增加梵钻收入变更记录
//		 code =amSeqService.getCode(FZBGCORD_PER_FIX+1);
//		FzGoldChangeRecord fzGoldChangeRecord2=new FzGoldChangeRecord();
//		fzGoldChangeRecord2.setRecordCode(code);
//		fzGoldChangeRecord2.setInOrOut("0");
//		fzGoldChangeRecord2.setGoldType("0");
//		fzGoldChangeRecord2.setBalance((long) 0);   //写死为0
//		fzGoldChangeRecord2.setNumber(coin_count);  //变更数量为赠送数量
//		fzGoldChangeRecord2.setUserid(praiserUser.getUserid());
//		fzGoldChangeRecordService.save(fzGoldChangeRecord2);

    }

    private void addRecord(String inOrOut, String userid, Long coin_count, String outGoldType, int type) {

        //增加梵钻支出变更记录
        String code = amSeqService.getCode(FZBGCORD_PER_FIX);
        FzGoldChangeRecord fzGoldChangeRecord1 = new FzGoldChangeRecord();
        fzGoldChangeRecord1.setRecordCode(code);
        fzGoldChangeRecord1.setInOrOut(inOrOut);
        fzGoldChangeRecord1.setGoldType(outGoldType);
        fzGoldChangeRecord1.setBalance((double) 0);
        fzGoldChangeRecord1.setNumber(coin_count.doubleValue());  //变更数量为赠送数量
        fzGoldChangeRecord1.setUserid(userid);
        fzGoldChangeRecord1.setIsNewRecord(true);
        fzGoldChangeRecord1.setType(type);
        fzGoldChangeRecord1.setMsgDate(new Date());
		// 文件
		String path = K3Config.BASEDIR +  "/" + FzTask.sendMsgFlag + "/addRecord.txt";
		// 获取的标识
		String flag = ReadFile.ReadToString(path);
		// false 代表不执行 true 代表执行
		if ("true".equals(flag)) {
//			rabbitTemplate.convertAndSend("fzMsg1", fzGoldChangeRecord1);
			rabbitTemplate.convertAndSend(FzTask.fzMsgQueueP1, fzGoldChangeRecord1);
		}
		fzGoldChangeRecordService.save(fzGoldChangeRecord1);
	}

    /**
     * 更新状态
     *
     * @param fzAppreciationRecord
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(FzAppreciationRecord fzAppreciationRecord) {
        super.updateStatus(fzAppreciationRecord);
    }

    /**
     * 删除数据
     *
     * @param fzAppreciationRecord
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(FzAppreciationRecord fzAppreciationRecord) {
        fzAppreciationRecordDao.deleteDb(fzAppreciationRecord.getAppreciationCode());
        fzAppreciationFollowDao.deleteDbByRecCode(fzAppreciationRecord.getAppreciationCode());
//		super.delete(fzAppreciationRecord);
//		FzAppreciationFollow fzAppreciationFollow = new FzAppreciationFollow();
//		fzAppreciationFollow.setAppreciationCode(fzAppreciationRecord);
//		fzAppreciationFollowDao.delete(fzAppreciationFollow);
    }

    @Transactional(readOnly = false)
    public void saveFzAppreciationFollow(FzAppreciationFollow fzAppreciationFollow, DingUser praiserUser, DingUser presenterUser, String outGoldType, FzAppreciationRecord fzAppreciationRecord) {
        fzAppreciationFollow.setAppreciationCode(fzAppreciationRecord);
        Long coin_count = fzAppreciationFollow.getCoinNumber();
        fzAppreciationFollowDao.insert(fzAppreciationFollow);
        if (fzAppreciationFollow.getCoinNumber() != null) {
            fzAppreciationRecord.setCoinCount(fzAppreciationRecord.getCoinCount() + fzAppreciationFollow.getCoinNumber());
            coin_count = fzAppreciationFollow.getCoinNumber();
        } else {
            fzAppreciationRecord.setCoinCount(fzAppreciationRecord.getCoinCount() + 1);
            coin_count = (long) 1;
        }
        fzAppreciationRecord.setPraiserNumber(fzAppreciationRecord.getPraiserNumber() + 1);
        super.save(fzAppreciationRecord);
        //跟赞者，减
        dingUserService.save(presenterUser);
        //增加梵钻支出变更记录
        addRecord("1", presenterUser.getUserid(), coin_count, outGoldType, 2);
        //获赞者，加
        dingUserService.save(praiserUser);
        //增加梵钻收入变更记录
        addRecord("0", praiserUser.getUserid(), coin_count, "0", 2);


    }

    @Transactional
    public LeaderboardData getUserInfo(String praiserId) {
        return fzAppreciationFollowDao.getUserInfo(praiserId);
    }

    @Transactional
    public LeaderboardData getUserInfo2(String userId) {
        return fzAppreciationFollowDao.getUserInfo2(userId);
    }

    public List<FzAppreciationRecord> getListByPraiserNumber(Date startTime, Date endTime) {
        return fzAppreciationFollowDao.getListByPraiserNumber(startTime, endTime);
    }

    public List<FzAppreciationFollow> getFollowsByCode(String appreciationCode) {
        return fzAppreciationFollowDao.getFollowsByCode(appreciationCode);
    }

    public List<FzAppreciationRecord> getListByTimeAndType(Date startTime, Date endTime, String type) {
        return fzAppreciationFollowDao.getListByTimeAndType(startTime, endTime, type);
    }

    public List<FzAppreciationRecord> getRecodeByTimeAndType(Date startTime, Date endTime, String type, int pageSize, int pageNo) {
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = pageSize;
        return fzAppreciationFollowDao.getRecodeByTimeAndType(startTime, endTime, type, startIndex, endIndex);
    }

    public List<FzAppreciationRecord> getRecodeByTime(Date startTime, Date endTime, int pageSize, int pageNo) {
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = pageSize;
        return fzAppreciationFollowDao.getRecodeByTime(startTime, endTime, startIndex, endIndex);
    }

    public int getTotal(Date startTime, Date endTime, String type) {
        return fzAppreciationRecordDao.getTotal(startTime, endTime, type);
    }

    public List<String> getFollowsByUserId(String userId) {
        return fzAppreciationFollowDao.getFollowsByUserId(userId);
    }

    /**
     * 根据时间查询赞赏记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<FzAppreciationRecord> getRecordByTime(Date startTime, Date endTime) {
        return fzAppreciationFollowDao.getRecordByTime(startTime, endTime);
    }

    /**
     * 根据赞赏记录主键获取跟赞记录
     *
     * @param appreciation
     * @return
     */
    public List<FzAppreciationFollow> getFollows(List<String> appreciation) {
        return fzAppreciationFollowDao.getFollows(appreciation);
    }

    /**
     * 获取当前连接的数据库名
     *
     * @return
     */
    public String getDataBase() {
        return fzAppreciationRecordDao.getDataBase();
    }


    public List<FzAppreciationRecord> getTypeRecordByTime(Date startTime, Date endTime) {
        return fzAppreciationFollowDao.getTypeRecordByTime(startTime, endTime);
    }

    //获赞者获赞数量
    @Transactional(readOnly = true)
    public List<ExportAppreciationData> getExportData() {
        return fzAppreciationRecordDao.getExportData();
    }

    //赠送者赞赏数量+跟赞数量
    @Transactional(readOnly = true)
    public List<ExportAppreciationData> getExportData1() {
        return fzAppreciationRecordDao.getExportData1();
    }


    /**
     * 根据时间类型获取不同时间类型的束时间
     *
     * @return
     */
    public Date getEndTime(int type, String monthStartTime) throws ParseException {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = monthStartTime.substring(0, 5);
        Date endTime = null;
        if (type == 1) {
            //  星期就不用这个选项了
        } else if (type == 2) {
            //获取下个月时间
            int nextMonth = Integer.parseInt(monthStartTime.substring(5, 7)) + 1;
            //如果是12月
            if (nextMonth == 13) {
                endTime = new Timestamp(longSdf.parse(Integer.parseInt(monthStartTime.substring(0, 4)) + 1 + "-01-01 00:00:00").getTime());
            } else {
                endTime = new Timestamp(longSdf.parse(year + nextMonth + "-01 00:00:00").getTime());
            }
        } else if (type == 3) {
            int month = Integer.parseInt(monthStartTime.substring(5, 7)); //得到第几月
            int i;  //第几季度
            if (month / 3 == 0) {
                i = month / 3;
            } else {
                i = month / 3 + 1;
            }
            if (i == 1) {
                endTime = new Timestamp(longSdf.parse(year + "04-01 00:00:00").getTime());
            } else if (i == 2) {
                endTime = new Timestamp(longSdf.parse(year + "07-01 00:00:00").getTime());
            } else if (i == 3) {
                endTime = new Timestamp(longSdf.parse(year + "10-01 00:00:00").getTime());
            } else if (i == 4) {
                endTime = new Timestamp(longSdf.parse(year + "12-31 12:59:59").getTime());
            }
        } else if (type == 4) {
            //下一年年初
            Integer thisYear = Integer.parseInt(monthStartTime.substring(0, 4)) + 1;
            endTime = new Timestamp(longSdf.parse(thisYear + "-01-01 00:00:00").getTime());
        }
        return endTime;
    }

    /**
     * 获取动态效果的赞赏记录
     *
     * @param type    月季度年,自己选 1:星期 2:月 3:季度 4:年
     * @param ranking 代表梵赞记录滑动效果前几名
     *                monthStartTime 前端传个月份第一天的时间 "2018-11-01 00:00:00"
     * @return
     * @throws Exception
     */
    public ReturnInfo getSlideAppreciation(int type, int ranking, String departmentId, String monthStartTime) throws Exception {
        Date startTime = getStartDateByMonthStartTime(type, monthStartTime);
        Date endTime = getEndDateByMonthStartTime(type,monthStartTime);
        Set<FzAppreciationSlideRecord> slideList = new HashSet<>();
        List<DingUser> praiserList = new ArrayList<>(0);
        List<DingUser> presenterList = new ArrayList<>(0);
        if ("0".equals(departmentId)) {
            //赞记录里面前ranking名中在规定时间里面的排行中获赞者钉钉信息
            praiserList = fzAppreciationRecordDao.getSlidePraiserDate(startTime, endTime, ranking);
            //梵赞记录里面前ranking名中在规定时间里面的排行中赞他人者钉钉信息
            presenterList = fzAppreciationRecordDao.getSlidePresenterDate(startTime, endTime, ranking);
        } else {
            departmentId = "%" + departmentId + "%";
            //赞记录里面前ranking名中在规定时间里面的排行中获赞者钉钉信息
            praiserList = fzAppreciationRecordDao.getPraiserDateByDapartmentId(startTime, endTime, departmentId, ranking);
            //梵赞记录里面前ranking名中在规定时间里面的排行中赞他人者钉钉信息
            presenterList = fzAppreciationRecordDao.getPresenterDateByDapartmentId(startTime, endTime, departmentId, ranking);
        }
        if (praiserList != null && praiserList.size() != 0 && presenterList != null && presenterList.size() != 0) {
            //将每个记录里面中的获赞者与被赞者信息都存到实体类中
            for (int i = 0; i < praiserList.size(); i++) {
                FzAppreciationSlideRecord record = new FzAppreciationSlideRecord();
                DingUser praiserUser = praiserList.get(i);
                DingUser presenterUser = presenterList.get(i);
                if(praiserUser != null && presenterUser != null){
                    record.setPraiserId(praiserUser.getId());
                    record.setPraiserName(praiserUser.getName());
                    record.setPraiserAvatar(praiserUser.getAvatar());
                    record.setPresenterId(presenterUser.getId());
                    record.setPresenterName(presenterUser.getName());
                    record.setPresenterAvatar(presenterUser.getAvatar());
                    slideList.add(record);
                }
            }
            return ReturnDate.success(slideList);
        } else {
            return ReturnDate.success(15001,"没有梵赞赞赏数据",null);
        }
    }

    /**
     * @param type   月季度年,自己选 1:星期 2:月 3:季度 4:年
     * @param userId 钉钉id
     * monthStartTime 前端传个月份第一天的时间 "2018-11-01 00:00:00"
     * @return
     */
    public ReturnInfo getSlideDate(int type, String userId, String monthStartTime) throws Exception {
        Date startTime = getStartDateByMonthStartTime(type, monthStartTime);
        Date endTime = getEndDateByMonthStartTime(type,monthStartTime);
        FzAppreciationDate date = fzAppreciationRecordDao.getPraisDate(startTime, endTime, userId);
        //没被赞过
        if (date == null) {
            date = new FzAppreciationDate();
            date.setLeaderboards(0L);
            date.setCoinCounts(0L);
            date.setPraiserNum(0L);
        }
        FzAppreciationDate presentDate = fzAppreciationRecordDao.getPresentDate(startTime, endTime, userId);
        date.setUserid(userId);
        Long presenterNum = presentDate.getPresenterNum();
        date.setPresenterNum(presenterNum);
        date.setCoinNumber(presentDate.getCoinNumber());
        //没赞过其他人
        if (presenterNum == 0L) {
            date.setCoinNumber(0L);
        }
        return ReturnDate.success(date);
    }

    //查询当天跟赞者给了获赞者多少梵赞了
    public int countThidDay(String presenterId, String praiserId) throws Exception{
        Date date = new Date();
        Timestamp dayStartTime = TimeUtils.getDayStartTime(date);
        Timestamp dayEndTime = TimeUtils.getDayEndTime(date);
        //获取当天送了多少梵赞,不包括跟赞
        int recordNum = fzAppreciationRecordDao.countThidDay(dayStartTime, dayEndTime, presenterId, praiserId);
        //得到当天的跟赞数量
        int followNum = fzAppreciationFollowDao.countFollowThisDay(dayStartTime, dayEndTime, presenterId, praiserId);
        return recordNum + followNum;
    }

    /**
     * 根据时间类型获取不同时间类型的开始时间  ,在写sql的时候用<,不要用<=
     * @param type 月季度年,自己选  2:月 3:季度 4:年
     * @param monthStartTime 前端传个月份第一天的时间 ,格式为:"2018-11-01 00:00:00"
     * @return
     */
    public static Date getStartDateByMonthStartTime(int type,  String monthStartTime)throws Exception{
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = monthStartTime.substring(0, 5);
        Date startTime = null;
        if (type == 1) {
            //startTime = TimeUtils.getBeginDayOfWeek();   星期就不用这个选项了
        } else if (type == 2) {
            startTime = new Timestamp(longSdf.parse(monthStartTime).getTime());
        } else if (type == 3) {
            int month = Integer.parseInt(monthStartTime.substring(5, 7)); //得到第几月
            int i;  //第几季度
            if (month / 3 == 0) {
                i = month / 3;
            } else {
                i = month / 3 + 1;
            }
            if (i == 1) {
                startTime = new Timestamp(longSdf.parse(year + "01-01 00:00:00").getTime());
            } else if (i == 2) {
                startTime = new Timestamp(longSdf.parse(year + "04-01 00:00:00").getTime());
            } else if (i == 3) {
                startTime = new Timestamp(longSdf.parse(year + "07-01 00:00:00").getTime());
            } else if (i == 4) {
                startTime = new Timestamp(longSdf.parse(year + "10-01 00:00:00").getTime());
            }
        } else if (type == 4) {
            Integer thisYear = Integer.parseInt(monthStartTime.substring(0, 4));
            startTime = new Timestamp(longSdf.parse(thisYear + "-01-01 00:00:00").getTime());
        }
        return startTime;
    }


    /**
     * 根据时间类型获取不同时间类型的结束时间
     * @param type 月季度年,自己选  2:月 3:季度 4:年
     * @param monthStartTime 前端传个月份第一天的时间 ,格式为:"2018-11-01 00:00:00"
     * @return
     */
    public static Date getEndDateByMonthStartTime(int type,  String monthStartTime)throws Exception{
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = monthStartTime.substring(0, 5);
        Date endTime = null;
        if (type == 1) {
            //  星期就不用这个选项了
        } else if (type == 2) {
            //获取下个月时间
            int nextMonth = Integer.parseInt(monthStartTime.substring(5, 7)) + 1;
            //如果是12月
            if (nextMonth == 13) {
                endTime = new Timestamp(longSdf.parse(Integer.parseInt(monthStartTime.substring(0, 4)) + 1 + "-01-01 00:00:00").getTime());
            } else {
                endTime = new Timestamp(longSdf.parse(year + nextMonth + "-01 00:00:00").getTime());
            }
        } else if (type == 3) {
            int month = Integer.parseInt(monthStartTime.substring(5, 7)); //得到第几月
            int i;  //第几季度
            if (month / 3 == 0) {
                i = month / 3;
            } else {
                i = month / 3 + 1;
            }
            if (i == 1) {
                endTime = new Timestamp(longSdf.parse(year + "04-01 00:00:00").getTime());
            } else if (i == 2) {
                endTime = new Timestamp(longSdf.parse(year + "07-01 00:00:00").getTime());
            } else if (i == 3) {
                endTime = new Timestamp(longSdf.parse(year + "10-01 00:00:00").getTime());
            } else if (i == 4) {
                endTime = new Timestamp(longSdf.parse(year + "12-31 12:59:59").getTime());
            }
        } else if (type == 4) {
            //下一年年初
            Integer thisYear = Integer.parseInt(monthStartTime.substring(0, 4)) + 1;
            endTime = new Timestamp(longSdf.parse(thisYear + "-01-01 00:00:00").getTime());
        }
        return endTime;
    }

    @Autowired
    private FzExpenditureRecordDao fzExpenditureRecordDao;
    /**
     * 梵赞收入和全部记录
     * @param
     */
    @Transactional(readOnly = true)
    public FzGoldRecord getAccountRecord(Page<FzAccountRecord> page, FzAccountRecord fzAccountRecord) {
        Integer pageNo = page.getPageNo();
        if (pageNo == 1) {
            pageNo = 0;
        } else {
            pageNo = page.getPageSize() * (pageNo-1);
        }
        // 查总数
        int count = fzAppreciationRecordDao.selectCount(fzAccountRecord.getUserId(), pageNo, page.getPageSize(), fzAccountRecord.getFlag());
        // 和查总数的sql一样如果需要改都要改成一样
        List<FzAccountRecord> fzAccountRecordList = fzAppreciationRecordDao.getAccountRecord(fzAccountRecord.getUserId(), pageNo, page.getPageSize(), fzAccountRecord.getFlag());

        DingUser dingUser = dingUserService.get(fzAccountRecord.getUserId());
        // 个人支出梵钻总和
        Double expendGold = fzExpenditureRecordDao.getExpendGold(fzAccountRecord.getUserId());
        // 剩余梵钻
        Double surplusGold = NumberUtils.sub(dingUser.getConvertibleGold().doubleValue(), expendGold);
        FzGoldRecord fzGoldRecord = new FzGoldRecord();
        fzGoldRecord.setCount(count);
        fzGoldRecord.setConvertibleGold(dingUser.getConvertibleGold().intValue());
        fzGoldRecord.setExpendGold(expendGold);
        fzGoldRecord.setSurplusGold(surplusGold);
        fzGoldRecord.setFzAccountRecordList(fzAccountRecordList);
        return fzGoldRecord;
    }
}