/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.group.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.jeesite.modules.asset.group.entity.PurchaseData;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.taobao.entity.TaobaoOrderRds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.group.entity.GroupPurchase;
import com.jeesite.modules.asset.group.dao.GroupPurchaseDao;
import com.jeesite.modules.asset.group.entity.GroupDetail;
import com.jeesite.modules.asset.group.dao.GroupDetailDao;

/**
 * 团购信息表Service
 * @author len
 * @version 2018-10-23
 */
@Service
@Transactional(readOnly=true)
public class GroupPurchaseService extends CrudService<GroupPurchaseDao, GroupPurchase> {
	
	@Autowired
	private GroupDetailDao groupDetailDao;
	@Autowired
	private GroupPurchaseDao groupPurchaseDao;
	
	/**
	 * 获取单条数据
	 * @param groupPurchase
	 * @return
	 */
	@Override
	public GroupPurchase get(GroupPurchase groupPurchase) {
		GroupPurchase entity = super.get(groupPurchase);
		if (entity != null){
			GroupDetail groupDetail = new GroupDetail(entity);
			groupDetail.setStatus(GroupDetail.STATUS_NORMAL);
			entity.setGroupDetailList(groupDetailDao.findList(groupDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param groupPurchase
	 * @return
	 */
	@Override
	public Page<GroupPurchase> findPage(Page<GroupPurchase> page, GroupPurchase groupPurchase) {
		return super.findPage(page, groupPurchase);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param groupPurchase
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(GroupPurchase groupPurchase) {
		super.save(groupPurchase);
		// 保存 GroupPurchase子表
		for (GroupDetail groupDetail : groupPurchase.getGroupDetailList()){
			if (!GroupDetail.STATUS_DELETE.equals(groupDetail.getStatus())){
				groupDetail.setPurchaseCode(groupPurchase);
				if (groupDetail.getIsNewRecord()){
					groupDetail.preInsert();
					groupDetailDao.insert(groupDetail);
				}else{
					groupDetail.preUpdate();
					groupDetailDao.update(groupDetail);
				}
			}else{
				groupDetailDao.delete(groupDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param groupPurchase
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(GroupPurchase groupPurchase) {
		super.updateStatus(groupPurchase);
	}
	
	/**
	 * 删除数据
	 * @param groupPurchase
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(GroupPurchase groupPurchase) {
		super.delete(groupPurchase);
//		GroupDetail groupDetail = new GroupDetail();
//		groupDetail.setPurchaseCode(groupPurchase);
		groupDetailDao.deleteDb(groupPurchase.getPurchaseCode());
	}

	/**
	 * 根据旺旺id或者获取团长信息
	 * @param wangCode
	 */
	public List<GroupPurchase> getGroupPurchase(String wangCode, String phone) {
		return groupPurchaseDao.getGroupPurchase(wangCode, phone);
	}

	/**
	 * 根据旺旺id + 获取团长信息
	 * @param wangCode
	 */
	public GroupPurchase getPurchase(String wangCode, String phone) {
		return groupPurchaseDao.getPurchase(wangCode, phone);
	}

	/**
	 * 查询团员旺旺ID是否存在小区拼团表中
	 * @param memberWangCode
	 * @return
	 */
	public List<PurchaseData> getMember(String memberWangCode) {
		return groupPurchaseDao.getMember(memberWangCode);
	}

	/**
	 * 根据旺旺id匹配买家昵称
	 */
	public List<TaobaoOrderRds> getOrderRds(List<String> list) {
		return groupPurchaseDao.getOrderRds(list);
	}

	public List<TbSku> selectBySkuList(List<String> list) {
		return groupPurchaseDao.selectBySkuList(list);
	}

	/**
	 * 根据电话查询团购表和明细表中是否存在这个电话
	 * @param phone
	 * @return
	 */
	public List<PurchaseData> selectByPhone(String phone) {
		return groupPurchaseDao.selectByPhone(phone);
	}

	/**
	 * 验证团员输入的旺旺id是否和现存的旺旺Id一样
	 * @param groupPurchase
	 * @return
	 */
	public String getMembers(GroupPurchase groupPurchase) {
		List<GroupPurchase> groupPurchaseList = this.findList(new GroupPurchase());
		List<GroupDetail> detailList = groupDetailDao.findList(new GroupDetail());
		for (GroupDetail groupDetail : groupPurchase.getGroupDetailList()) {
			GroupPurchase groupPurchase1 = null;

			try {
			    // 根据明细旺旺Id去主表中查询是否存在
				groupPurchase1 = groupPurchaseList.stream().filter(s ->s.getWangCode().equals(groupDetail.getMemberWangCode())).findFirst().get();

			}catch (NoSuchElementException e) {

			}
			// 如果存在则返回
			if (groupPurchase1 != null) {
				return groupPurchase1.getWangCode();
			}
			GroupDetail detail = null;
			try {
			    // 根据明细旺旺id去明细表查询是否存在
				detail = detailList.stream().filter(s -> s.getMemberWangCode().equals(groupDetail.getMemberWangCode())).findFirst().get();
			}catch (NoSuchElementException e) {
				return "";
			}
			if (detail != null) {
			    // 若存在，并且明细编码一样，说明是同一个明细旺旺id没有改变过
				if (detail.getDetailCode().equals(groupDetail.getDetailCode())) {
					continue;
				} else {
				    // 如果明细编码不一样，说明和明细表中存在的明细不一样
					return detail.getMemberWangCode();
				}
			}
		}
		return "";
	}
}