/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.draw.service;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.config.Global;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DepartmentUtil;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.fz.utils.common.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.draw.entity.LuckDraw;
import com.jeesite.modules.asset.draw.dao.LuckDrawDao;
import com.jeesite.modules.asset.draw.entity.LuckDetail;
import com.jeesite.modules.asset.draw.dao.LuckDetailDao;

import javax.annotation.Resource;

/**
 * 抽奖竞猜Service
 * @author len
 * @version 2018-10-05
 */
@Service
@Transactional(readOnly=true)
public class LuckDrawService extends CrudService<LuckDrawDao, LuckDraw> {
	
	@Autowired
	private LuckDetailDao luckDetailDao;
	@Autowired
	private LuckDrawDao luckDrawDao;
	@Resource
	private RedisTemplate<String, List> redisTemplate;
	private String RANDOMID = "r92CGREvVtp15loQ";
	/**
	 * 获取单条数据
	 * @param luckDraw
	 * @return
	 */
	@Override
	public LuckDraw get(LuckDraw luckDraw) {
		LuckDraw entity = super.get(luckDraw);
		if (entity != null){
			LuckDetail luckDetail = new LuckDetail(entity);
			luckDetail.setStatus(LuckDetail.STATUS_NORMAL);
			entity.setLuckDetailList(luckDetailDao.findList(luckDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param luckDraw
	 * @return
	 */
	@Override
	public Page<LuckDraw> findPage(Page<LuckDraw> page, LuckDraw luckDraw) {
		return super.findPage(page, luckDraw);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param luckDraw
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(LuckDraw luckDraw) {
		super.save(luckDraw);
		// 保存 LuckDraw子表
		for (LuckDetail luckDetail : luckDraw.getLuckDetailList()){
			if (!LuckDetail.STATUS_DELETE.equals(luckDetail.getStatus())){
				luckDetail.setDocumentCode(luckDraw);
				if (luckDetail.getIsNewRecord()){
					luckDetail.preInsert();
					luckDetailDao.insert(luckDetail);
				}else{
					luckDetail.preUpdate();
					luckDetailDao.update(luckDetail);
				}
			}else{
				luckDetailDao.delete(luckDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param luckDraw
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(LuckDraw luckDraw) {
		super.updateStatus(luckDraw);
	}
	
	/**
	 * 删除数据
	 * @param luckDraw
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(LuckDraw luckDraw) {
		super.delete(luckDraw);
		LuckDetail luckDetail = new LuckDetail();
		luckDetail.setDocumentCode(luckDraw);
		luckDetailDao.delete(luckDetail);
	}

	/**
	 * 根据单据号删除明细数据
	 * @param documentCode
	 */
	@Transactional(readOnly = false)
	public void deleteDetailByCode(String documentCode) {
		luckDetailDao.deleteDetailByCode(documentCode);
	}

	/**
	 * 根据单据表头【中奖人数】， 获取梵赞-用户管理档案数据， 【是否中奖】=0 并且 状态=【启用】的用户清单，随机获取
	 * @param winnersNum
	 * @return
	 */
	public List<DingUser> selectByRand(int winnersNum) {
		return luckDrawDao.selectByRand(winnersNum);
	}

	/**
	 * 根据从钉钉用户表中查出的复合中奖条件的随机数据插入抽奖明细表
	 * @param luckDraw
	 * @param dingUserList
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void inserDetail(LuckDraw luckDraw, List<DingUser> dingUserList) {
		super.save(luckDraw);
		List<LuckDetail> luckDetailList = ListUtils.newArrayList();
		List<DingUserDepartment> dingUserDepartmentList = redisTemplate.opsForValue().get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取缓存中所有部门
		List<DepartmentData> departmentList = redisTemplate.opsForValue().get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		for (DingUser dingUser : dingUserList) {
			// 部门获得
			String departmentName = DepartmentUtil.getDepartment(dingUser, dingUserDepartmentList, departmentList);
			LuckDetail luckDetail = new LuckDetail();
			// 单据编号
			luckDetail.setDocumentCode(luckDraw);
			// 钉钉用户id  用于给用户推送消息
			luckDetail.setUserId(dingUser.getUserid());
			// 工号
			luckDetail.setWorkCode(dingUser.getJobnumber());
			// 英文名
			luckDetail.setEmpName(dingUser.getName());
			// 部门
			luckDetail.setDepartment(departmentName);
			String extattr = dingUser.getExtattr();
			if (extattr.contains("中文名")) {
				String chineseName = JSONObject.parseObject(extattr).get("中文名").toString();
				// 中文名
				luckDetail.setChineseName(chineseName);
			}
			luckDetailList.add(luckDetail);
		}
		luckDetailDao.insertBatch(luckDetailList);
	}

	/**
	 * 获取中奖用户
	 * @param documentCode
	 * @param winnersNum
	 * @param winningCode
	 * @return
	 */
	@Transactional(readOnly = false)
	public String getPrizeUsers(String documentCode, int winnersNum, String winningCode) {
		this.deleteDetailByCode(documentCode);
		// 随机获取用户
		List<DingUser> dingUserList = this.selectByRand(winnersNum);
		if (dingUserList == null || dingUserList.size() <= 0) {
			return "没有合适的用户！";
		}
		LuckDraw luckDraw = this.get(documentCode);
		luckDraw.setWinnersNum(winnersNum);
		luckDraw.setWinningCode(winningCode);
		this.inserDetail(luckDraw, dingUserList);
		return "";
	}

	/**
	 * 更新抽奖表和用户表
	 * @param luckDraw
	 * @param dingUserList
	 */
	@Transactional(readOnly = false)
	public void saveData(LuckDraw luckDraw, List<DingUser> dingUserList) {
		luckDraw.setPushStatus("1");
		super.save(luckDraw);
		luckDrawDao.saveDingUser(dingUserList);
	}

	public String selectByKey() {
		return luckDrawDao.selectByKey();
	}
}