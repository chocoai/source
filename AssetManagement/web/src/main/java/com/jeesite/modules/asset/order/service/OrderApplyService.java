/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.service;

import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.order.entity.OrderApply;
import com.jeesite.modules.asset.order.dao.OrderApplyDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单变更申请表Service
 * @author czy
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly=true)
public class OrderApplyService extends CrudService<OrderApplyDao, OrderApply> {
	@Autowired
	private OrderApplyDao orderApplyDao;
	/**
	 * 总部部门编码
	 */
	private final String ZBCODE = "A1000000";
	/**
	 * 部门最根级父节点
	 */
	private final String ZERO = "0";
	/**
	 * 获取单条数据
	 * @param orderApply
	 * @return
	 */
	@Override
	public OrderApply get(OrderApply orderApply) {
		return super.get(orderApply);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param orderApply
	 * @return
	 */
	@Override
	public Page<OrderApply> findPage(Page<OrderApply> page, OrderApply orderApply) {
		String parentCode = EmpUtils.getOffice().getParentCode();
		if (!ZERO.equals(parentCode) && "employee".equals(UserUtils.getUser().getUserType())) {
			orderApply.setOfficeCodes(EmpUtils.getOffice().getOfficeCode());
			orderApply.setUserName(UserUtils.getUser().getUserName());
//			if ("employee".equals(UserUtils.getUser().getUserType())) {
//
//			}
		}
		return super.findPage(page, orderApply);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param orderApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(OrderApply orderApply) {
		super.save(orderApply);
	}
	
	/**
	 * 更新状态
	 * @param orderApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(OrderApply orderApply) {
		super.updateStatus(orderApply);
	}
	
	/**
	 * 删除数据
	 * @param orderApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(OrderApply orderApply) {
		super.delete(orderApply);
	}

	@Transactional(readOnly = true)
	public List<OrderApply> getSalesOrder (String salesOrder) {
		return orderApplyDao.getSalesOrder(salesOrder);
	}

	/**
	 * 根据单据编号删除订单
	 */
	@Transactional(readOnly = false)
	public void deleteDb(String documentCode) {
		orderApplyDao.deleteDb(documentCode);
	}

	@Transactional(readOnly = false)
	public Map saveMigrateData(OrderApply orderApply, String date) {
		Map<String, Object> map = new HashMap<>();
		try {
			super.save(orderApply);
		}catch (Exception e) {
			map.put("flag", "failure");
			map.put("code", "500");
			map.put("msg", "数据推送失败");
			map.put("time", date);
			return map;
		}
		map.put("flag", "success");
		map.put("code", "200");
		map.put("msg", "数据推送成功");
		map.put("time", date);
		return map;
	}


	@Transactional(readOnly = true)
	public List<User> getUser(String userName) {
		return orderApplyDao.getUser(userName);
	}

	/**
	 * 根据帐号获取部门编码
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getEmp(String loginCode) {
		return orderApplyDao.getOffice(loginCode);
	}
}