/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.storevideo.ovopark.api.ApiService;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkDevice;
import com.jeesite.modules.storevideo.util.DefaultEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkUser;
import com.jeesite.modules.storevideo.ovopark.dao.SvOvoparkUserDao;

/**
 * 万店掌用户Service
 * @author Philip Guan
 * @version 2019-02-19
 */
@Service
@Transactional(readOnly=true)
public class SvOvoparkUserService extends CrudService<SvOvoparkUserDao, SvOvoparkUser> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 获取单条数据
	 * @param svOvoparkUser
	 * @return
	 */
	@Override
	public SvOvoparkUser get(SvOvoparkUser svOvoparkUser) {
		return super.get(svOvoparkUser);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svOvoparkUser
	 * @return
	 */
	@Override
	public Page<SvOvoparkUser> findPage(Page<SvOvoparkUser> page, SvOvoparkUser svOvoparkUser) {
        Page<SvOvoparkUser> result = super.findPage(page, svOvoparkUser);
	    //applicationEventPublisher.publishEvent(result.getList().get(0));
		return result;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svOvoparkUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvOvoparkUser svOvoparkUser) {
        super.save(svOvoparkUser);
	}
	
	/**
	 * 更新状态
	 * @param svOvoparkUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvOvoparkUser svOvoparkUser) {
		super.updateStatus(svOvoparkUser);
	}
	
	/**
	 * 删除数据
	 * @param svOvoparkUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvOvoparkUser svOvoparkUser) {
		super.delete(svOvoparkUser);
	}

    //@EventListener(condition = "T(com.xxx.Order.BizType).MONTHLY eq #event.data")
    @EventListener
    @Async
    public void addDataS1(SvOvoparkUser event) {
        logger.debug("addDataS1");
    }
}