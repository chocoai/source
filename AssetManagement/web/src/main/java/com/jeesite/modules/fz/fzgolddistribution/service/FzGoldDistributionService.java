/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzgolddistribution.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.jeesite.common.config.Global;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.ReadFile;
import com.jeesite.modules.asset.ding.dao.DingUserDao;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.scheduledtask.K3Config;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.fzgoldchangerecord.entity.FzGoldChangeRecord;
import com.jeesite.modules.fz.fzgoldchangerecord.service.FzGoldChangeRecordService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.fzgolddistribution.entity.FzGoldDistribution;
import com.jeesite.modules.fz.fzgolddistribution.dao.FzGoldDistributionDao;

/**
 * 分配梵砖Service
 * @author dwh
 * @version 2018-09-21
 */
@Service
@Transactional(readOnly=true)
public class FzGoldDistributionService extends CrudService<FzGoldDistributionDao, FzGoldDistribution> {
	private static final String FZBGCORD_PER_FIX = "FZBG";
	@Autowired
	private FzGoldChangeRecordService fzGoldChangeRecordService;
	@Autowired
	private DingUserService dingUserService;
	@Autowired
	private AmqpTemplate rabbitTemplate;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private DingDepartmentService dingDepartmentService;
	@Autowired
	private DingUserDao dingUserDao;

	@Value("${file.baseDir}")
	String baseDir;
	/**
	 * 获取单条数据
	 * @param fzGoldDistribution
	 * @return
	 */
	@Override
	public FzGoldDistribution get(FzGoldDistribution fzGoldDistribution) {
		return super.get(fzGoldDistribution);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzGoldDistribution
	 * @return
	 */
	@Override
	public Page<FzGoldDistribution> findPage(Page<FzGoldDistribution> page, FzGoldDistribution fzGoldDistribution) {
		return super.findPage(page, fzGoldDistribution);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzGoldDistribution
	 * @param userIdList
	 * @param departmentIdList
	 */

	@Transactional(readOnly=false)
	public void save(FzGoldDistribution fzGoldDistribution, String documentStatus, List<String> userIdList, List<String> departmentIdList) {
		long codeCount=0;
		super.save(fzGoldDistribution);
		if (ParamentUntil.isBackString(documentStatus)) {
			long number = fzGoldDistribution.getNumber(); //分配钻币的数量
			if ("1".equals(documentStatus)) {    //如果是审核
				//要插入日志的对象集合
				List<FzGoldChangeRecord> fzGoldChangeRecords1=new ArrayList<>();    //部门内梵砖
				List<FzGoldChangeRecord> fzGoldChangeRecords2=new ArrayList<>();   //跨部门梵砖
				if (fzGoldDistribution.getDistributionType().equals("0")) {
					//更新在职人员的梵钻
					dingUserService.updateGold(Math.round((number * 0.3)),Math.round( (number * 0.7)));
					DingUser dingUser=new DingUser();
					dingUser.setleft("0");
					List<DingUser> dingUsers = dingUserService.findList(dingUser);
					if (ParamentUntil.isBackList(dingUsers)) {
						for (int i = 0; i < dingUsers.size(); i++) {
							FzGoldChangeRecord fzGoldChangeRecord1=getEntity(null,  Math.round((number * 0.3) ), dingUsers.get(i).getUserid(), "1");
							FzGoldChangeRecord fzGoldChangeRecord2=getEntity(null,   Math.round( (number * 0.7)), dingUsers.get(i).getUserid(), "2");
							fzGoldChangeRecords1.add(fzGoldChangeRecord1);
							fzGoldChangeRecords2.add(fzGoldChangeRecord2);
						}
					}

				} else if (fzGoldDistribution.getDistributionType().equals("1")) {        //部门
					//查询部门右子节点得所有部门
					for (int i = 0; i < departmentIdList.size(); i++) {
						Set<String> departmentIds = dingDepartmentService.getIdByParntsId(departmentIdList.get(i));
						departmentIds.add(departmentIdList.get(i));  //把本级部门加入进去
						if (departmentIds != null) {
							for (String id : departmentIds) {
								//更新某个部门下的用户
								dingUserService.updateGoldBydepartmentId(Math.round((number * 0.3)),Math.round( (number * 0.7)),id);
								List<DingUser> dingUsers = dingUserDao.getUserListByDepartmentId(id);
								if (dingUsers != null) {
									for (int h = 0; h < dingUsers.size(); h++) {
										FzGoldChangeRecord fzGoldChangeRecord1=getEntity(null,  Math.round((number * 0.3) ), dingUsers.get(h).getUserid(), "1");
										FzGoldChangeRecord fzGoldChangeRecord2=getEntity(null,   Math.round( (number * 0.7)), dingUsers.get(h).getUserid(), "2");
										fzGoldChangeRecords1.add(fzGoldChangeRecord1);
										fzGoldChangeRecords2.add(fzGoldChangeRecord2);
									}
								}
							}
						} else {
							//更新某个部门下的用户
							dingUserService.updateGoldBydepartmentId(Math.round((number * 0.3)),Math.round( (number * 0.7)),departmentIdList.get(i));
							//如果部门没有下级，直接找本级的
							List<DingUser> dingUsers = dingUserDao.getUserListByDepartmentId(departmentIdList.get(i));
							if (ParamentUntil.isBackList(dingUsers)) {
								for (int j = 0; j < dingUsers.size(); j++) {
										FzGoldChangeRecord fzGoldChangeRecord1=getEntity(null,  Math.round((number * 0.3) ), dingUsers.get(j).getUserid(), "1");
										FzGoldChangeRecord fzGoldChangeRecord2=getEntity(null,   Math.round( (number * 0.7)), dingUsers.get(j).getUserid(), "2");
										fzGoldChangeRecords1.add(fzGoldChangeRecord1);
										fzGoldChangeRecords2.add(fzGoldChangeRecord2);
								}
							}
						}
					}
				} else if (fzGoldDistribution.getDistributionType().equals("2")) {         //员工
					for (int i = 0; i < userIdList.size(); i++) {
						DingUser dingUser = dingUserService.get(userIdList.get(i));
						if (dingUser != null) {
							dingUser.setInDepartmentGold(Math.round((number * 0.3)));
							dingUser.setOutDepartmentGold(Math.round((number * 0.7) ));
							dingUserService.save(dingUser);

							FzGoldChangeRecord fzGoldChangeRecord1=getEntity(null,  Math.round((number * 0.3) ), dingUser.getUserid(), "1");
							FzGoldChangeRecord fzGoldChangeRecord2=getEntity(null,   Math.round( (number * 0.7)), dingUser.getUserid(), "2");
							fzGoldChangeRecords1.add(fzGoldChangeRecord1);
							fzGoldChangeRecords2.add(fzGoldChangeRecord2);
						}
					}
				} else {     //特权用户
					for (int i = 0; i < userIdList.size(); i++) {
						DingUser dingUser = dingUserService.get(userIdList.get(i));
						if (dingUser != null) {
							if("0".equals(fzGoldDistribution.getFzType())){
								//0:部门内梵赞
								dingUser.setInDepartmentGold(dingUser.getInDepartmentGold() + number);
							}else if("1".equals(fzGoldDistribution.getFzType())){
								//1:跨部门梵赞
								dingUser.setOutDepartmentGold(dingUser.getOutDepartmentGold() + number);
							}
							dingUserService.save(dingUser);
							//type那里因为两个表的类型那里相差了1
							FzGoldChangeRecord fzGoldChangeRecord2=getEntity(fzGoldDistribution.getRemarks(), number, dingUser.getUserid(), Integer.parseInt(fzGoldDistribution.getFzType())+1+"");
							fzGoldChangeRecords2.add(fzGoldChangeRecord2);
						}
					}
				}
				if (ParamentUntil.isBackList(fzGoldChangeRecords1)){
				fzGoldChangeRecordService.insetBatch(fzGoldChangeRecords1);
				}
				if (ParamentUntil.isBackList(fzGoldChangeRecords2)){
				fzGoldChangeRecordService.insetBatch(fzGoldChangeRecords2);
				}
			}
		}

	}

	/**
	 *  保存FzGoldChangeRecord
	 * @param name
	 * @param number
	 * @param userid
	 * @param type
	 * @return
	 */
	private FzGoldChangeRecord getEntity(String name, long number, String userid, String type) {
		if (!ParamentUntil.isBackString(name)){
			Date date=new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			name=format.format(date)+"月初梵钻充值";
		}
		String  code =amSeqService.getCode(FZBGCORD_PER_FIX);
		FzGoldChangeRecord fzGoldChangeRecord=new FzGoldChangeRecord();
		fzGoldChangeRecord.setRecordName(name);
		fzGoldChangeRecord.setRecordCode(UUID.randomUUID().toString());
		fzGoldChangeRecord.setInOrOut("0");  //0:收入，1：支出
		fzGoldChangeRecord.setGoldType(type);
		fzGoldChangeRecord.setBalance((long) 0);   //余额写死为0
		fzGoldChangeRecord.setNumber(number);  //变更数量为赠送数量
		fzGoldChangeRecord.setUserid(userid);
		fzGoldChangeRecord.setIsNewRecord(true);
		fzGoldChangeRecord.setStatus("0");
		fzGoldChangeRecord.setCreateBy(UserUtils.getUser().getLoginCode());
		Date date=new Date();
		fzGoldChangeRecord.setCreateDate(date);
		fzGoldChangeRecord.setUpdateBy(UserUtils.getUser().getLoginCode());
		fzGoldChangeRecord.setUpdateDate(date);
		fzGoldChangeRecord.setMsgDate(new Date());
		String path = K3Config.BASEDIR +  "/" + FzTask.sendMsgFlag + "/saveRecode.txt";
		// 获取的标识
		String flag = ReadFile.ReadToString(path);
		// false 代表不执行 true 代表执行
		if ("true".equals(flag)) {
//			rabbitTemplate.convertAndSend("fzMsg1", fzGoldChangeRecord);
			rabbitTemplate.convertAndSend(FzTask.fzMsgQueueP1, fzGoldChangeRecord);
		}
		return fzGoldChangeRecord;
	}

	private void saveRecode(String name,long number, String userid,String type) {
		if (!ParamentUntil.isBackString(name)){
		Date date=new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 name=format.format(date)+"月初梵钻充值";
		}
//		codeCount++;
		String  code =amSeqService.getCode(FZBGCORD_PER_FIX);
		FzGoldChangeRecord fzGoldChangeRecord=new FzGoldChangeRecord();
		fzGoldChangeRecord.setRecordName(name);
		fzGoldChangeRecord.setRecordCode(code);
		fzGoldChangeRecord.setInOrOut("0");
		fzGoldChangeRecord.setGoldType(type);
		fzGoldChangeRecord.setBalance((long) 0);   //写死为0
		fzGoldChangeRecord.setNumber(number);  //变更数量为赠送数量
		fzGoldChangeRecord.setUserid(userid);
		fzGoldChangeRecord.setIsNewRecord(true);
		fzGoldChangeRecord.setMsgDate(new Date());
		//fzGoldChangeRecord.setType(3);
		// 文件
		String path = K3Config.BASEDIR +  "/" + FzTask.sendMsgFlag + "/saveRecode.txt";
		// 获取的标识
		String flag = ReadFile.ReadToString(path);
		// false 代表不执行 true 代表执行
		if ("true".equals(flag)) {
//			rabbitTemplate.convertAndSend("fzMsg1", fzGoldChangeRecord);
			rabbitTemplate.convertAndSend(FzTask.fzMsgQueueP1, fzGoldChangeRecord);
		}
		fzGoldChangeRecordService.save(fzGoldChangeRecord);
	}

	/**
	 * 更新状态
	 * @param fzGoldDistribution
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzGoldDistribution fzGoldDistribution) {
		super.updateStatus(fzGoldDistribution);
	}
	
	/**
	 * 删除数据
	 * @param fzGoldDistribution
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzGoldDistribution fzGoldDistribution) {
		super.delete(fzGoldDistribution);
	}

	/**
	 * 用户根据钉钉id扣除梵赞
	 * @param nums 需要兑换的梵赞数量
	 * @param userid
	 * @return
	 */
	@Transactional(readOnly=false)
    public ReturnInfo fzConsume(String nums, String userid) throws Exception{
    	//根据钉钉id获取可以兑换的梵赞
		DingUser userById = dingUserDao.getUserById(userid);
		Long convertibleCold = userById.getConvertibleGold();
		long fzNum = Long.parseLong(nums);
		//用户不够梵赞
		if(fzNum > convertibleCold){
			return ReturnDate.error(900,"您的梵赞不足");
		}
		//扣除梵赞
		synchronized (FzGoldDistributionService.class){
			dingUserDao.updateConvertibleCold(userid,nums);
		}
		return ReturnDate.success("兑换成功");
	}
}