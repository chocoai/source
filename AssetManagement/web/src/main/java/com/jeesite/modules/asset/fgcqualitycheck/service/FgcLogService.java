/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.fgcqualitycheck.entity.FgcLog;
import com.jeesite.modules.asset.fgcqualitycheck.dao.FgcLogDao;

/**
 * 梵工厂反写日志表Service
 * @author len
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly=true)
public class FgcLogService extends CrudService<FgcLogDao, FgcLog> {
	@Autowired
	private FgcLogDao fgcLogDao;
	/**
	 * 获取单条数据
	 * @param fgcLog
	 * @return
	 */
	@Override
	public FgcLog get(FgcLog fgcLog) {
		return super.get(fgcLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fgcLog
	 * @return
	 */
	@Override
	public Page<FgcLog> findPage(Page<FgcLog> page, FgcLog fgcLog) {
		return super.findPage(page, fgcLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fgcLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FgcLog fgcLog) {
		super.save(fgcLog);
	}
	
	/**
	 * 更新状态
	 * @param fgcLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FgcLog fgcLog) {
		super.updateStatus(fgcLog);
	}
	
	/**
	 * 删除数据
	 * @param fgcLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FgcLog fgcLog) {
		super.delete(fgcLog);
	}


	@Transactional(readOnly = false)
	public void saveData(String operationType, String errorInfo, String fentityId, String fid) {

		FgcLog fgcLog = new FgcLog();
		if (fentityId != null) {
			fgcLog = fgcLogDao.getByEntityId(fentityId);
			if (fgcLog != null) {
				if (errorInfo.equals(fgcLog.getErrorInfo())) {
					saveLog(operationType, errorInfo, fentityId, fid);
				}
			} else {
				saveLog(operationType, errorInfo, fentityId, fid);
			}
		} else if (fid != null) {
			fgcLog = fgcLogDao.getByFid(fid);
			if (fgcLog != null) {
				if (errorInfo.equals(fgcLog.getErrorInfo())) {
					saveLog(operationType, errorInfo, fentityId, fid);
				}
			} else {
				saveLog(operationType, errorInfo, fentityId, fid);
			}
		}
	}

	@Transactional(readOnly = false)
	public void saveLog(String operationType, String errorInfo, String fentityId, String fid) {
		FgcLog fgcLog = new FgcLog();
		fgcLog.setCreateTime(new Date());
		fgcLog.setOperationType(operationType);
		fgcLog.setErrorInfo(errorInfo);
		fgcLog.setFentityId(fentityId);
		fgcLog.setFid(fid);
		super.save(fgcLog);
	}
}