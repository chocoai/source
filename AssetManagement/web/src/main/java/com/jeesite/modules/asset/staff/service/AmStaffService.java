/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.staff.service;

import java.util.List;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.asset.staff.dao.AmStaffPostDao;
import com.jeesite.modules.asset.staff.entity.AmStaffPost;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.staff.entity.AmStaff;
import com.jeesite.modules.asset.staff.dao.AmStaffDao;

/**
 * 员工资料表Service
 * @author czy
 * @version 2018-04-26
 */
@Service
@Transactional(readOnly=true)
public class AmStaffService extends CrudService<AmStaffDao, AmStaff> {
	@Autowired
	private AmStaffPostDao amStaffPostDao;
	@Autowired
	private AmStaffDao amStaffDao;
	/**
	 * 获取单条数据
	 * @param amStaff
	 * @return
	 */
	@Override
	public AmStaff get(AmStaff amStaff) {
		return super.get(amStaff);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amStaff
	 * @return
	 */
	@Override
	public Page<AmStaff> findPage(Page<AmStaff> page, AmStaff amStaff) {
		return super.findPage(page, amStaff);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amStaff
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmStaff amStaff) {
		this.updateStatus(amStaff);
		super.save(amStaff);
		// 保存员工岗位
		AmStaffPost where = new AmStaffPost();
		where.setStaffCode(amStaff.getStaffCode());
		amStaffPostDao.deleteByEntity(where);
		if (ListUtils.isNotEmpty(amStaff.getAmStaffPostList())){
			for (AmStaffPost e : amStaff.getAmStaffPostList()){
				e.setStaffCode(amStaff.getStaffCode());
			}
			amStaffPostDao.insertBatch(amStaff.getAmStaffPostList());
		}
	}
	
	/**
	 * 更新状态
	 * @param amStaff
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmStaff amStaff) {
		amStaffDao.updateStatus(amStaff);
	}
	
	/**
	 * 删除数据
	 * @param amStaff
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmStaff amStaff) {
//		super.delete(amStaff);
		amStaffDao.deleteDb(amStaff.getStaffCode());   // 删除员工资料表
		amStaffPostDao.deleteDb(amStaff.getStaffCode());  // 删除员工岗位表
	}

	/**
	 * 查询当前员工关联的岗位信息
	 */
	public List<AmStaffPost> findAmStaffPostList(AmStaff amStaff){
		AmStaffPost amStaffPost = new AmStaffPost();
		amStaffPost.setStaffCode(amStaff.getStaffCode());
		return amStaffPostDao.findList(amStaffPost);
	}

	/**
	 * 岗位查询
	 * @param staffCode
	 * @return
	 */
	public List<String> findPostName(String staffCode) {
		return amStaffPostDao.findPostName(staffCode);
	}
}