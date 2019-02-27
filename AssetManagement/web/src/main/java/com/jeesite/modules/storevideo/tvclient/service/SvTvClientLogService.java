/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClientLog;
import com.jeesite.modules.storevideo.tvclient.dao.SvTvClientLogDao;

/**
 * 电视客户端日志Service
 * @author Philip Guan
 * @version 2019-02-13
 */
@Service
@Transactional(readOnly=true)
public class SvTvClientLogService extends CrudService<SvTvClientLogDao, SvTvClientLog> {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 获取单条数据
	 * @param svTvClientLog
	 * @return
	 */
	@Override
	public SvTvClientLog get(SvTvClientLog svTvClientLog) {
		return super.get(svTvClientLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svTvClientLog
	 * @return
	 */
	@Override
	public Page<SvTvClientLog> findPage(Page<SvTvClientLog> page, SvTvClientLog svTvClientLog) {
		return super.findPage(page, svTvClientLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svTvClientLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvTvClientLog svTvClientLog) {
		super.save(svTvClientLog);
	}
	
	/**
	 * 更新状态
	 * @param svTvClientLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvTvClientLog svTvClientLog) {
		super.updateStatus(svTvClientLog);
	}
	
	/**
	 * 删除数据
	 * @param svTvClientLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvTvClientLog svTvClientLog) {
		super.delete(svTvClientLog);
	}

    /**
     * 新增日志
     * @param ip
     * @param logType   info,err,warning,debug
     * @param message   <2000字符
     */
    @Transactional(readOnly=false)
    public void addLog(String logType, String ip, String message) {
        SvTvClientLog log = new SvTvClientLog();
        log.setIsNewRecord(true);
        log.setIp(ip);
        //log.setOnline(online);
        log.setLogType(logType);
        log.setLogMessage(message);
        log.setLogDate(new Date());
        save(log);
    }
}