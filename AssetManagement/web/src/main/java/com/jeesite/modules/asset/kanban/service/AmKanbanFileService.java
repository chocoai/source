/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.asset.kanban.entity.AmKanbanFile;
import com.jeesite.modules.asset.kanban.dao.AmKanbanFileDao;

/**
 * 看板档案Service
 * @author dwh
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly=true)
public class AmKanbanFileService extends TreeService<AmKanbanFileDao, AmKanbanFile> {
	
	/**
	 * 获取单条数据
	 * @param amKanbanFile
	 * @return
	 */
	@Autowired
	private AmKanbanFileDao kanbanFileDao;
	@Override
	public AmKanbanFile get(AmKanbanFile amKanbanFile) {
		return super.get(amKanbanFile);
	}
	
	/**
	 * 查询列表数据
	 * @param amKanbanFile
	 * @return
	 */
	@Override
	public List<AmKanbanFile> findList(AmKanbanFile amKanbanFile) {
		return super.findList(amKanbanFile);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amKanbanFile
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmKanbanFile amKanbanFile) {
		super.save(amKanbanFile);
	}
	
	/**
	 * 更新状态
	 * @param amKanbanFile
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmKanbanFile amKanbanFile) {
		kanbanFileDao.updateStatus(amKanbanFile);
	}
	
	/**
	 * 删除数据
	 * @param amKanbanFile
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmKanbanFile amKanbanFile) {
//		super.delete(amKanbanFile);
		kanbanFileDao.deleteDb(amKanbanFile.getKanbanCode());
	}


	public boolean getAmKanbanFileByNameAndCode(String kanbanName, String kanbanCode) {
		boolean rst = false;
		List<AmKanbanFile> list = kanbanFileDao.getKanbanFileByName(kanbanName);
		//查不到，新建，查得到判断名字和code是否是同一条数据
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (kanbanCode != null && kanbanCode.length() > 0 && list.get(i).getKanbanCode().equals(kanbanCode)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return rst;
	}
	
}