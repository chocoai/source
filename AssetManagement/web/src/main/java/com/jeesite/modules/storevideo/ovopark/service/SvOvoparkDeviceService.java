/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkUser;
import com.jeesite.modules.storevideo.util.DefaultEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkDevice;
import com.jeesite.modules.storevideo.ovopark.dao.SvOvoparkDeviceDao;

import java.util.List;

/**
 * 万店掌设备Service
 * @author Philip Guan
 * @version 2019-02-18
 */
@Service
@Transactional(readOnly=true)
public class SvOvoparkDeviceService extends CrudService<SvOvoparkDeviceDao, SvOvoparkDevice> implements ApplicationEventPublisherAware {

	@Autowired
	SvOvoparkDeviceDao svOvoparkDeviceDao;

    private ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

	/**
	 * 获取单条数据
	 * @param svOvoparkDevice
	 * @return
	 */
	@Override
	public SvOvoparkDevice get(SvOvoparkDevice svOvoparkDevice) {
		return super.get(svOvoparkDevice);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svOvoparkDevice
	 * @return
	 */
	@Override
	public Page<SvOvoparkDevice> findPage(Page<SvOvoparkDevice> page, SvOvoparkDevice svOvoparkDevice) {
        Page<SvOvoparkDevice> result = super.findPage(page, svOvoparkDevice);

        //List<SvOvoparkDevice> event = result.getList();
        //this.applicationEventPublisher.publishEvent(new DefaultEvent<>(event.get(0)));

        return result;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svOvoparkDevice
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvOvoparkDevice svOvoparkDevice) {
		super.save(svOvoparkDevice);
	}
	
	/**
	 * 更新状态
	 * @param svOvoparkDevice
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvOvoparkDevice svOvoparkDevice) {
		super.updateStatus(svOvoparkDevice);
	}
	
	/**
	 * 删除数据
	 * @param svOvoparkDevice
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvOvoparkDevice svOvoparkDevice) {
		super.delete(svOvoparkDevice);
	}

	@Transactional(readOnly=false)
	public void deleteAll(){
		svOvoparkDeviceDao.deleteAll();
	}

    @Transactional(readOnly=false)
    public void insertBatch(List<SvOvoparkDevice> list){
        svOvoparkDeviceDao.insertBatch(list);
    }



	@EventListener
	public void addDataS2(DefaultEvent<SvOvoparkDevice> event) {
		//System.out.println(JSONObject.toJSONString(entity.getData()));
        System.out.println("addDataS2");
	}
}